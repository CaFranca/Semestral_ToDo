package br.edu.ifsp.spo.todolist.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Lista tarefas com base em filtros combinados:
     * - status (pendentes, fazendo, concluídas ou todas)
     * - tag específica (opcional)
     * - data de vencimento (opcional)
     * - ordenação por ID (asc/desc)
     */
    public List<Tarefa> listar(
            String filtroStatus,
            String filtroTag,
            LocalDate dataInicio,
            LocalDate dataFim,
            String ordem,
            User usuario
    ) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        Status statusEnum = null;
        if (filtroStatus != null && !filtroStatus.isBlank() && !filtroStatus.equalsIgnoreCase("todas")) {
            // Convert "concluidas" to "concluida" before enum conversion
            if ("concluidas".equalsIgnoreCase(filtroStatus)) {
                filtroStatus = "concluida";
            }
            statusEnum = Status.valueOf(filtroStatus.toUpperCase());
        }



// Busca simples (sem tag e sem datas)
     if ((filtroTag == null || filtroTag.isBlank()) && dataInicio == null && dataFim == null) {
         if (statusEnum != null) {
             return switch (statusEnum) {
                 case PENDENTE -> ordenar(tarefasRepository.findByUserAndStatus(usuario, Status.PENDENTE), ordem);
                 case FAZENDO -> ordenar(tarefasRepository.findByUserAndStatus(usuario, Status.FAZENDO), ordem);
                 case CONCLUIDA -> ordenar(tarefasRepository.findByUserAndStatus(usuario, Status.CONCLUIDA), ordem);
             };
         } else {
             return ordenar(tarefasRepository.findByUser(usuario), ordem);
         }
     }

// Busca avançada com intervalo de datas
     return ordenar(
             tarefasRepository.filtrarTarefas(
                     usuario,
                     statusEnum,
                     filtroTag,
                     dataInicio,
                     dataFim
             ),
             ordem
     );

    }

    /**
     * Adiciona nova tarefa vinculada ao usuário
     */
    public void adicionarNovaTarefa(String texto, LocalDate dataVencimento, Status status, Set<String> tags, User usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        var novaTarefa = Tarefa.construirCom(texto, status, dataVencimento, tags);
        novaTarefa.setUser(usuario);
        tarefasRepository.save(novaTarefa);
    }

    /**
     * Alterar status somente se a tarefa pertencer ao usuário
     */
    public void alterarStatus(Long id, Status novoStatus, User usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        var tarefa = tarefasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        if (!tarefa.getUser().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Usuário não autorizado para alterar esta tarefa");
        }

        tarefa.alterarStatus(novoStatus);
        tarefasRepository.save(tarefa);
    }

    public void apagarTarefa(Long id, User usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        var tarefa = tarefasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        if (!tarefa.getUser().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Usuário não autorizado para alterar esta tarefa");
        }

        tarefasRepository.delete(tarefa);
    }

    public Tarefa buscarTarefaEdicao(Long id, User usuario) { // acha a tarefa a ser editada
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        var tarefa = tarefasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        if (!tarefa.getUser().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Usuário não autorizado para alterar esta tarefa");
        }

        return tarefa;
    }

    public void editarTarefa(Long id, Tarefa dadosForm, User usuarioLogado){ // edita e salva as infos
        Tarefa tarefa = this.buscarTarefaEdicao(id, usuarioLogado);

        tarefa.setTexto(dadosForm.getTexto());
        tarefa.setStatus(dadosForm.getStatus());
        tarefa.setDataVencimento(dadosForm.getDataVencimento());
        tarefa.setTags(dadosForm.getTags());

        tarefasRepository.save(tarefa);
    }

    /**
     * Aplica ordenação por ID
     */
    private List<Tarefa> ordenar(List<Tarefa> tarefas, String ordem) {
        if ("desc".equalsIgnoreCase(ordem)) {
            tarefas.sort((a, b) -> b.getId().compareTo(a.getId()));
        } else {
            tarefas.sort((a, b) -> a.getId().compareTo(b.getId()));
        }
        return tarefas;
    }

    public Set<String> listarTagsExistentes(){
        return tarefasRepository.findAll().stream().flatMap(t -> t.getTags().stream()).collect(Collectors.toSet());
    }

}
