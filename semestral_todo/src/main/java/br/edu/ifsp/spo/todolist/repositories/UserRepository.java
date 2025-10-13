package br.edu.ifsp.spo.todolist.repositories;

import br.edu.ifsp.spo.todolist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> findByResetToken(String resetToken);

    // Add methods for admin functionality
    List<User> findByRole(String role);
    long countByRole(String role);
    List<User> findAllByOrderByCreatedAtDesc();
}