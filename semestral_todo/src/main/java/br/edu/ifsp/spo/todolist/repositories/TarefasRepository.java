package br.edu.ifsp.spo.todolist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifsp.spo.todolist.models.Tarefa;
import br.edu.ifsp.spo.todolist.models.Tarefa.Status;
import br.edu.ifsp.spo.todolist.models.User;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefa, Long> {

    // Lista todas as tarefas com um determinado status
    List<Tarefa> findByStatus(Status status);

    // Lista todas as tarefas de um usuário específico
    List<Tarefa> findByUser(User user);

    // Lista tarefas por usuário e status (útil para filtros mais refinados)
    List<Tarefa> findByUserAndStatus(User user, Status status);
}
