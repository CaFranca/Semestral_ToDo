package br.edu.ifsp.spo.todolist.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifsp.spo.todolist.models.Tarefa;
import br.edu.ifsp.spo.todolist.models.Tarefa.Status;
import br.edu.ifsp.spo.todolist.models.User;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefa, Long> {

    // Lista todas as tarefas de um usuário específico
    List<Tarefa> findByUser(User user);

    // Lista tarefas por usuário e status
    List<Tarefa> findByUserAndStatus(User user, Status status);

    // Lista tarefas por usuário e data de vencimento
    List<Tarefa> findByUserAndDataVencimento(User user, LocalDate dataVencimento);

    // Lista tarefas por usuário ordenadas por ID
    List<Tarefa> findByUserOrderByIdAsc(User user);
    List<Tarefa> findByUserOrderByIdDesc(User user);

    // Busca por tag usando JPQL (assumindo que Tarefa tem um Set<String> tags)
    @Query("SELECT t FROM Tarefa t JOIN t.tags tag WHERE t.user = :user AND tag = :tag")
    List<Tarefa> findByUserAndTag(@Param("user") User user, @Param("tag") String tag);

    // Busca combinando status, data e tag
    @Query("""
    SELECT t FROM Tarefa t
    JOIN t.tags tag
    WHERE t.user = :user
      AND (:status IS NULL OR t.status = :status)
      AND (:tag IS NULL OR tag = :tag)
      AND (:dataInicio IS NULL OR t.dataVencimento >= :dataInicio)
      AND (:dataFim IS NULL OR t.dataVencimento <= :dataFim)
    ORDER BY t.id ASC
""")
    List<Tarefa> filtrarTarefas(
            @Param("user") User user,
            @Param("status") Status status,
            @Param("tag") String tag,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );

}
