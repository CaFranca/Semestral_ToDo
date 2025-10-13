package br.edu.ifsp.spo.todolist.services;

import br.edu.ifsp.spo.todolist.models.User;
import br.edu.ifsp.spo.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false) // Make it optional for development
    private JavaMailSender mailSender;

    private final Random random = new Random();

    // Option 1: Generate reset token (for direct password change)
    public boolean generateResetToken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false; // Email não encontrado
        }

        User user = userOpt.get();
        String token = generateRandomString(32);

        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(24)); // Token válido por 24h

        userRepository.save(user);

        // Send email or fallback to console
        sendResetEmail(user.getEmail(), token, null, "token");
        return true;
    }

    // Option 2: Generate reset key (6-digit code)
    public boolean generateResetKey(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        String key = generateNumericKey(6);

        user.setResetKey(key);
        user.setResetKeyExpiry(LocalDateTime.now().plusHours(1)); // Key válida por 1h

        userRepository.save(user);

        // Send email or fallback to console
        sendResetEmail(user.getEmail(), null, key, "key");
        return true;
    }

    // Reset password with token (Option 1)
    public boolean resetPasswordWithToken(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return false; // Token expirado
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);
        return true;
    }

    // Reset password with key (Option 2)
    public boolean resetPasswordWithKey(String email, String key, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        if (!key.equals(user.getResetKey()) ||
                user.getResetKeyExpiry().isBefore(LocalDateTime.now())) {
            return false; // Key inválida ou expirada
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetKey(null);
        user.setResetKeyExpiry(null);

        userRepository.save(user);
        return true;
    }

    // Validate reset key
    public boolean validateResetKey(String email, String key) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        return key.equals(user.getResetKey()) &&
                user.getResetKeyExpiry().isAfter(LocalDateTime.now());
    }

    private void sendResetEmail(String email, String token, String key, String method) {
        if (mailSender != null) {
            try {
                // Send real email
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject("Redefinição de Senha - Taskzz");

                if ("token".equals(method)) {
                    String resetLink = "http://localhost:8080/reset-password?token=" + token;
                    message.setText(
                            "Olá!\n\n" +
                                    "Você solicitou a redefinição da sua senha no Taskzz.\n\n" +
                                    "Clique no link abaixo para redefinir sua senha:\n" +
                                    resetLink + "\n\n" +
                                    "Este link é válido por 24 horas.\n\n" +
                                    "Se você não solicitou esta redefinição, ignore este email.\n\n" +
                                    "Atenciosamente,\nEquipe Taskzz"
                    );
                } else {
                    message.setText(
                            "Olá!\n\n" +
                                    "Você solicitou a redefinição da sua senha no Taskzz.\n\n" +
                                    "Seu código de verificação é: " + key + "\n\n" +
                                    "Digite este código na página de redefinição de senha.\n" +
                                    "Este código é válido por 1 hora.\n\n" +
                                    "Se você não solicitou esta redefinição, ignore este email.\n\n" +
                                    "Atenciosamente,\nEquipe Taskzz"
                    );
                }

                mailSender.send(message);
                System.out.println(" Email enviado com sucesso para: " + email);

                // Also print to console for development
                printToConsole(email, token, key, method);

            } catch (Exception e) {
                System.err.println(" Erro ao enviar email: " + e.getMessage());
                System.err.println(" Fallback para modo console...");
                // Fallback to console output
                printToConsole(email, token, key, method);
            }
        } else {
            // Development: Print to console
            System.out.println("🔧 Modo desenvolvimento - Email impresso no console:");
            printToConsole(email, token, key, method);
        }
    }

    private void printToConsole(String email, String token, String key, String method) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(" PASSWORD RESET INFORMATION");
        System.out.println("=".repeat(50));
        System.out.println(" Email: " + email);

        if ("token".equals(method)) {
            System.out.println(" Method: Link de redefinição");
            System.out.println(" Reset Token: " + token);
            System.out.println(" Reset Link: http://localhost:8080/reset-password?token=" + token);
            System.out.println(" Válido por: 24 horas");
        } else {
            System.out.println(" Method: Código de verificação");
            System.out.println(" Reset Key: " + key);
            System.out.println(" Válido por: 1 hora");
        }

        System.out.println("=".repeat(50));
        System.out.println();
    }

    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String generateNumericKey(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}