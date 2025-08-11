package br.edu.ifsp.spo.todolist.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.todolist.models.Tarefa;
import br.edu.ifsp.spo.todolist.models.Tarefa.Status;
import br.edu.ifsp.spo.todolist.models.User;
import br.edu.ifsp.spo.todolist.repositories.TarefasRepository;

@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;

    public TarefasService(TarefasRepository tarefasRepository) {
        this.tarefasRepository = tarefasRepository;
    }

    // Método para listar tarefas filtrando por usuário
    public List<Tarefa> listar(String filtro, User usuario) {
        if (usuario == null) {
            // Opcional: pode lançar erro ou retornar lista vazia se usuário não estiver logado
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        return switch (filtro.toLowerCase()) {
            case "pendentes" -> tarefasRepository.findByUserAndStatus(usuario, Status.PENDENTE);
            case "fazendo" -> tarefasRepository.findByUserAndStatus(usuario, Status.FAZENDO);
            case "concluidas" -> tarefasRepository.findByUserAndStatus(usuario, Status.CONCLUIDA);
            default -> tarefasRepository.findByUser(usuario);
        };
    }

    // Adicionar tarefa vinculando ao usuário
    public void adicionarNovaTarefa(String texto, java.time.LocalDate dataVencimento, Status status, Set<String> tags, User usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        var novaTarefa = Tarefa.construirCom(texto, status, dataVencimento, tags);
        novaTarefa.setUser(usuario);  // importante definir o usuário!
        tarefasRepository.save(novaTarefa);
    }

    // Alterar status somente se a tarefa pertencer ao usuário
    public void alterarStatus(Long id, Status novoStatus, User usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        var tarefa = tarefasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        // Verifica se a tarefa pertence ao usuário
        if (!tarefa.getUser().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Usuário não autorizado para alterar esta tarefa");
        }

        tarefa.alterarStatus(novoStatus);
        tarefasRepository.save(tarefa);
    }
}
