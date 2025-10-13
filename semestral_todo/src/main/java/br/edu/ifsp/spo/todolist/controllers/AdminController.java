package br.edu.ifsp.spo.todolist.controllers;

import br.edu.ifsp.spo.todolist.models.Tarefa;
import br.edu.ifsp.spo.todolist.models.User;
import br.edu.ifsp.spo.todolist.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Admin dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalUsers", adminService.getTotalUsers());
        model.addAttribute("totalTarefas", adminService.getTotalTarefas());
        model.addAttribute("completedTarefas", adminService.getCompletedTarefas());
        model.addAttribute("pendingTarefas", adminService.getPendingTarefas());
        model.addAttribute("adminUsers", adminService.getAdminUsersCount());
        return "admin/dashboard";
    }

    // User management
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = adminService.getUserById(id).orElse(null);
        if (user == null) {
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user,
                             RedirectAttributes redirectAttributes) {
        User updatedUser = adminService.updateUser(id, user);
        if (updatedUser != null) {
            redirectAttributes.addFlashAttribute("success", "Usuário atualizado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar usuário.");
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = adminService.deleteUser(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("success", "Usuário deletado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar usuário.");
        }
        return "redirect:/admin/users";
    }

    // Tarefa management (using Tarefa instead of Task)
    @GetMapping("/tarefas")
    public String listTarefas(Model model) {
        List<Tarefa> tarefas = adminService.getAllTarefas();
        model.addAttribute("tarefas", tarefas);
        return "admin/tarefas";
    }

    @GetMapping("/tarefas/edit/{id}")
    public String editTarefaForm(@PathVariable Long id, Model model) {
        Tarefa tarefa = adminService.getTarefaById(id).orElse(null);
        if (tarefa == null) {
            return "redirect:/admin/tarefas";
        }
        model.addAttribute("tarefa", tarefa);
        model.addAttribute("statusOptions", Tarefa.Status.values());
        return "admin/edit-tarefa";
    }

    @PostMapping("/tarefas/update/{id}")
    public String updateTarefa(@PathVariable Long id, @ModelAttribute Tarefa tarefa,
                               RedirectAttributes redirectAttributes) {
        Tarefa updatedTarefa = adminService.updateTarefa(id, tarefa);
        if (updatedTarefa != null) {
            redirectAttributes.addFlashAttribute("success", "Tarefa atualizada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar tarefa.");
        }
        return "redirect:/admin/tarefas";
    }

    @PostMapping("/tarefas/delete/{id}")
    public String deleteTarefa(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = adminService.deleteTarefa(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("success", "Tarefa deletada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar tarefa.");
        }
        return "redirect:/admin/tarefas";
    }

    // User tarefas view
    @GetMapping("/users/{userId}/tarefas")
    public String viewUserTarefas(@PathVariable Long userId, Model model) {
        User user = adminService.getUserById(userId).orElse(null);
        if (user == null) {
            return "redirect:/admin/users";
        }
        List<Tarefa> tarefas = adminService.getUserTarefas(userId);
        model.addAttribute("user", user);
        model.addAttribute("tarefas", tarefas);
        return "admin/user-tarefas";
    }
}