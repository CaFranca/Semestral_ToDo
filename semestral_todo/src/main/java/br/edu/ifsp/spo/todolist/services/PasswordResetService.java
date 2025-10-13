package br.edu.ifsp.spo.todolist.services;

import br.edu.ifsp.spo.todolist.models.User;
import br.edu.ifsp.spo.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        // For development: Print token to console
        System.out.println("=== PASSWORD RESET TOKEN ===");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Reset Token: " + token);
        System.out.println("Reset Link: http://localhost:8080/reset-password?token=" + token);
        System.out.println("============================");

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

        // For development: Print key to console
        System.out.println("=== PASSWORD RESET KEY ===");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Reset Key: " + key);
        System.out.println("===========================");

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