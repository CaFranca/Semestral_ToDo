package br.edu.ifsp.spo.todolist.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.todolist.models.Tarefa;
import br.edu.ifsp.spo.todolist.models.Tarefa.Status;
import br.edu.ifsp.spo.todolist.repositories.TarefasRepository;

@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;

    public TarefasService(TarefasRepository tarefasRepository) {
        this.tarefasRepository = tarefasRepository;
    }

    public List<Tarefa> listar(String filtro) {
        return switch (filtro.toLowerCase()) {
            case "pendentes" -> this.tarefasRepository.findByStatus(Status.PENDENTE);
            case "fazendo" -> this.tarefasRepository.findByStatus(Status.FAZENDO);
            case "concluidas" -> this.tarefasRepository.findByStatus(Status.CONCLUIDA);
            default -> this.tarefasRepository.findAll();
        };
    }

    public List<Tarefa> listarTodas() {
        return this.tarefasRepository.findAll();
    }

    // Novo método para adicionar com todos os campos
    public void adicionarNovaTarefa(String texto, java.time.LocalDate dataVencimento, Status status, Set<String> tags) {
        var novaTarefa = Tarefa.construirCom(texto, status, dataVencimento, tags);
        this.tarefasRepository.save(novaTarefa);
    }

    // Método genérico para alterar status
    public void alterarStatus(Long id, Status novoStatus) {
        var tarefa = this.tarefasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        tarefa.alterarStatus(novoStatus);
        this.tarefasRepository.save(tarefa);
    }
}
