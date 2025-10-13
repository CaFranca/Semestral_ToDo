package br.edu.ifsp.spo.todolist.controllers;

import br.edu.ifsp.spo.todolist.models.Task;
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
        model.addAttribute("totalTasks", adminService.getTotalTasks());
        model.addAttribute("completedTasks", adminService.getCompletedTasks());
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

    // Task management
    @GetMapping("/tasks")
    public String listTasks(Model model) {
        List<Task> tasks = adminService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "admin/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Task task = adminService.getTaskById(id).orElse(null);
        if (task == null) {
            return "redirect:/admin/tasks";
        }
        model.addAttribute("task", task);
        model.addAttribute("users", adminService.getAllUsers());
        return "admin/edit-task";
    }

    @PostMapping("/tasks/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task,
                             RedirectAttributes redirectAttributes) {
        Task updatedTask = adminService.updateTask(id, task);
        if (updatedTask != null) {
            redirectAttributes.addFlashAttribute("success", "Tarefa atualizada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar tarefa.");
        }
        return "redirect:/admin/tasks";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = adminService.deleteTask(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("success", "Tarefa deletada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar tarefa.");
        }
        return "redirect:/admin/tasks";
    }

    // User tasks view
    @GetMapping("/users/{userId}/tasks")
    public String viewUserTasks(@PathVariable Long userId, Model model) {
        User user = adminService.getUserById(userId).orElse(null);
        if (user == null) {
            return "redirect:/admin/users";
        }
        List<Task> tasks = adminService.getUserTasks(userId);
        model.addAttribute("user", user);
        model.addAttribute("tasks", tasks);
        return "admin/user-tasks";
    }
}