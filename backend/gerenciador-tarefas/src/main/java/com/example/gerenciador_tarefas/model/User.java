package com.example.gerenciador_tarefas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    // Se quiser: OneToMany para tasks
    // @OneToMany(mappedBy = "usuario")
    // private List<Task> tasks;
}
