package br.edu.ifsp.spo.todolist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifsp.spo.todolist.models.Tarefa;
import br.edu.ifsp.spo.todolist.models.Tarefa.Status;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByStatus(Status status);

}
