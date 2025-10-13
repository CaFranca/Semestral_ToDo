package br.edu.ifsp.spo.todolist.services;

import br.edu.ifsp.spo.todolist.models.Task;
import br.edu.ifsp.spo.todolist.models.User;
import br.edu.ifsp.spo.todolist.repositories.TaskRepository;
import br.edu.ifsp.spo.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // User management methods
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        }).orElse(null);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            // Delete user's tasks first
            List<Task> userTasks = taskRepository.findByUserId(id);
            taskRepository.deleteAll(userTasks);

            // Then delete user
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Task management methods
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());
            task.setDueDate(taskDetails.getDueDate());
            return taskRepository.save(task);
        }).orElse(null);
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Statistics
    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalTasks() {
        return taskRepository.count();
    }

    public long getCompletedTasks() {
        return taskRepository.countByCompleted(true);
    }

    public long getAdminUsersCount() {
        return userRepository.countByRole("ADMIN");
    }
}