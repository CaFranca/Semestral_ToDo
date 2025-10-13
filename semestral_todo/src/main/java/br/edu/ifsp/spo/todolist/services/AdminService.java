package br.edu.ifsp.spo.todolist.services;

import br.edu.ifsp.spo.todolist.models.Tarefa;
import br.edu.ifsp.spo.todolist.models.User;
import br.edu.ifsp.spo.todolist.repositories.TarefasRepository;
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
    private TarefasRepository tarefasRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // User management methods
    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByCreatedAtDesc();
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
            // Delete user's tarefas first
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                List<Tarefa> userTarefas = tarefasRepository.findByUser(user);
                tarefasRepository.deleteAll(userTarefas);
            }
            // Then delete user
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Tarefa management methods
    public List<Tarefa> getAllTarefas() {
        return tarefasRepository.findAllByOrderByIdDesc();
    }

    public List<Tarefa> getUserTarefas(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return tarefasRepository.findByUser(user);
        }
        return List.of();
    }

    public Optional<Tarefa> getTarefaById(Long id) {
        return tarefasRepository.findById(id);
    }

    public Tarefa updateTarefa(Long id, Tarefa tarefaDetails) {
        return tarefasRepository.findById(id).map(tarefa -> {
            tarefa.setTexto(tarefaDetails.getTexto());
            tarefa.setStatus(tarefaDetails.getStatus());
            tarefa.setDataVencimento(tarefaDetails.getDataVencimento());
            tarefa.setTags(tarefaDetails.getTags());
            return tarefasRepository.save(tarefa);
        }).orElse(null);
    }

    public boolean deleteTarefa(Long id) {
        if (tarefasRepository.existsById(id)) {
            tarefasRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Statistics - UPDATED to use repository methods
    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalTarefas() {
        return tarefasRepository.count();
    }

    public long getCompletedTarefas() {
        return tarefasRepository.countByStatus(Tarefa.Status.CONCLUIDA);
    }

    public long getPendingTarefas() {
        return tarefasRepository.countByStatus(Tarefa.Status.PENDENTE);
    }

    public long getAdminUsersCount() {
        return userRepository.countByRole("ADMIN");
    }
}