package br.edu.ifsp.spo.todolist.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifsp.spo.todolist.models.Tarefa;
import br.edu.ifsp.spo.todolist.repositories.TarefasRepository;

@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;

    //Aqui temos um exemplo de injeção de dependência, onde o repositório é injetado no serviço
    //Isso permite que o serviço utilize os métodos do repositório para realizar operações no banco
    //de dados, como buscar, salvar, atualizar e excluir tarefas.
    //O Spring irá gerenciar a criação e a injeção do repositório automaticamente
    public TarefasService(TarefasRepository tarefasRepository) {
        this.tarefasRepository = tarefasRepository;
    }

    public List<Tarefa> listar(String filtro) {
        return switch (filtro.toLowerCase()) {
            case "pendentes" ->
                this.tarefasRepository.findByConcluida(false);
            case "concluidas" -> 
                this.tarefasRepository.findByConcluida(true);
            default -> 
                this.tarefasRepository.findAll();
        };
    }

    public List<Tarefa> listarTodas() {
        return this.tarefasRepository.findAll();
    }

    public void adicionarNovaTarefa(String texto) {
        var novaTarefa = Tarefa.construirCom(texto, false);

        this.tarefasRepository.save(novaTarefa);
    }

    public void marcarComoConcluida(long id) {
        var tarefaASerAlterada = this.tarefasRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        if(!tarefaASerAlterada.isConcluida()){
            tarefaASerAlterada.alterarStatus();
            this.tarefasRepository.save(tarefaASerAlterada);
        }
    }

    public void marcarComoNaoConcluida(long id) {
        var tarefaASerAlterada = this.tarefasRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        if(tarefaASerAlterada.isConcluida()) {
            tarefaASerAlterada.alterarStatus();
            this.tarefasRepository.save(tarefaASerAlterada);
        }           
    }
}
