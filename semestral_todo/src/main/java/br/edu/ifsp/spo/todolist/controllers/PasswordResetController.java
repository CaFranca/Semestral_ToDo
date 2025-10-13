package br.edu.ifsp.spo.todolist.controllers;

import br.edu.ifsp.spo.todolist.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    // Show forgot password page
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password"; // This stays in templates root
    }

    // Process forgot password request
    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @RequestParam String email,
            @RequestParam String method, // "token" or "key"
            RedirectAttributes redirectAttributes) {

        boolean success;
        if ("token".equals(method)) {
            success = passwordResetService.generateResetToken(email);
        } else {
            success = passwordResetService.generateResetKey(email);
        }

        if (success) {
            redirectAttributes.addFlashAttribute("success",
                    "Instruções de redefinição foram enviadas para seu email.");
        } else {
            redirectAttributes.addFlashAttribute("error",
                    "Email não encontrado.");
        }

        return "redirect:/forgot-password";
    }

    // Show reset password form (for token method)
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset/reset-password-token"; // Updated path
    }

    // Process reset password (token method)
    @PostMapping("/reset-password")
    public String processResetPassword(
            @RequestParam String token,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "As senhas não coincidem.");
            return "redirect:/reset-password?token=" + token;
        }

        boolean success = passwordResetService.resetPasswordWithToken(token, password);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Senha redefinida com sucesso!");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Token inválido ou expirado.");
            return "redirect:/reset-password?token=" + token;
        }
    }

    // Show reset password with key form
    @GetMapping("/reset-password-key")
    public String showResetPasswordKeyForm() {
        return "reset/reset-password-key"; // Updated path
    }

    // Verify reset key
    @PostMapping("/verify-reset-key")
    public String verifyResetKey(
            @RequestParam String email,
            @RequestParam String key,
            Model model,
            RedirectAttributes redirectAttributes) {

        boolean isValid = passwordResetService.validateResetKey(email, key);
        if (isValid) {
            model.addAttribute("email", email);
            model.addAttribute("key", key);
            return "reset/reset-password-final"; // Updated path
        } else {
            redirectAttributes.addFlashAttribute("error", "Código inválido ou expirado.");
            return "redirect:/reset-password-key";
        }
    }

    // Process final password reset (key method)
    @PostMapping("/reset-password-final")
    public String processResetPasswordFinal(
            @RequestParam String email,
            @RequestParam String key,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "As senhas não coincidem.");
            return "redirect:/reset-password-key"; // Fixed redirect path
        }

        boolean success = passwordResetService.resetPasswordWithKey(email, key, password);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Senha redefinida com sucesso!");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao redefinir senha.");
            return "redirect:/reset-password-key"; // Fixed redirect path
        }
    }
}