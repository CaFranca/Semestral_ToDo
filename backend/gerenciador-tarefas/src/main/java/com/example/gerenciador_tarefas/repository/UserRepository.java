package com.example.gerenciador_tarefas.repository;

import com.example.gerenciador_tarefas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
