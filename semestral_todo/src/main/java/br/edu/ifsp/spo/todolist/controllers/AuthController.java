package br.edu.ifsp.spo.todolist.controllers;

import br.edu.ifsp.spo.todolist.models.User;
import br.edu.ifsp.spo.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, RedirectAttributes redirectAttributes) {

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            redirectAttributes.addFlashAttribute("error", "A senha deve ter no mínimo 6 caracteres.");
            return "redirect:/register";
        }

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email já está em uso.");
            return "redirect:/register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // Set default role
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Conta criada com sucesso! Faça login.");
        return "redirect:/login";
    }

    // Admin registration page
    @GetMapping("/register-admin")
    public String showAdminRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register-admin";
    }

    @PostMapping("/register-admin")
    public String registerAdmin(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String adminCode,
                                RedirectAttributes redirectAttributes) {
        // Simple admin code validation
        if (!"ADMIN123".equals(adminCode)) {
            redirectAttributes.addFlashAttribute("error", "Código administrativo inválido.");
            return "redirect:/register-admin";
        }

        // Check if email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email já está em uso.");
            return "redirect:/register-admin";
        }

        // Create new admin user
        User user = new User(name, email, password, "ADMIN");
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Conta de administrador criada com sucesso! Faça login.");
        return "redirect:/login";
    }
}