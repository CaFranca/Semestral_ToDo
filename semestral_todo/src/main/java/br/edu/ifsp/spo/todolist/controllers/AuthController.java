package br.edu.ifsp.spo.todolist.controllers;

import br.edu.ifsp.spo.todolist.models.User;
import br.edu.ifsp.spo.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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
        return "register";  // sem extensão
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {

        // Verifica se o usuário já existe
        if (userRepository.findByName(user.getName()).isPresent()) {
            model.addAttribute("errorMessage", "Usuário já existente");
            model.addAttribute("user", user);
            return "register";
        }

        // Verifica se o e-mail já está registrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("errorMessage", "E-mail já registrado");
            model.addAttribute("user", user);
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Senha codificada: " + user.getPassword());
        userRepository.save(user);
        System.out.println("Usuário salvo no banco");
        return "redirect:/login";
    }

}

