package br.edu.ifsp.spo.todolist.repositories;

import br.edu.ifsp.spo.todolist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
}

