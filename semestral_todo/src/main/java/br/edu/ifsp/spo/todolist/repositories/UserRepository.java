package br.edu.ifsp.spo.todolist.repositories;

import br.edu.ifsp.spo.todolist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> findByResetToken(String resetToken); // Add this method
}