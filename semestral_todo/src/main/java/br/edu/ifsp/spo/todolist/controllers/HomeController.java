package br.edu.ifsp.spo.todolist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import br.edu.ifsp.spo.todolist.models.User;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User usuarioLogado) {
        if (usuarioLogado == null) {
            return "redirect:/login";  // não logado
        } else {
            return "redirect:/tarefas"; // logado
        }
    }
}
