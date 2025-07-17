package br.edu.ifsp.spo.todolist.controllers;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.edu.ifsp.spo.todolist.models.Tarefa.Status;
import br.edu.ifsp.spo.todolist.services.TarefasService;
import br.edu.ifsp.spo.todolist.forms.TarefaForm;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

    private final TarefasService service;

    public TarefasController(TarefasService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(Model model, @RequestParam(required = false, defaultValue = "todas") String filtro) {
        var tarefas = service.listar(filtro);
        model.addAttribute("tarefas", tarefas);
        model.addAttribute("tarefaForm", new TarefaForm()); // importante para o formulário
        return "tarefas/index.html";
    }

    @PostMapping("")
    public String create(@ModelAttribute TarefaForm tarefaForm) {
        if (tarefaForm.getTexto() == null || tarefaForm.getTexto().isBlank()) {
            return "redirect:/tarefas";
        }

        // Converter tagsString em Set<String>
        Set<String> tags = Set.of();
        if (tarefaForm.getTagsString() != null && !tarefaForm.getTagsString().isBlank()) {
            tags = Arrays.stream(tarefaForm.getTagsString().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
        }

        // Usa o status recebido ou PENDENTE por padrão
        service.adicionarNovaTarefa(
                tarefaForm.getTexto(),
                tarefaForm.getDataVencimento(),
                tarefaForm.getStatus() != null ? tarefaForm.getStatus() : Status.PENDENTE,
                tags
        );

        return "redirect:/tarefas";
    }

    // Marcar uma tarefa com qualquer status válido: PENDENTE, FAZENDO, CONCLUIDA
    @GetMapping("/{id}/marcar-como/{status}")
    public String marcarComo(@PathVariable Long id, @PathVariable String status) {
        try {
            Status novoStatus = Status.valueOf(status.toUpperCase());
            service.alterarStatus(id, novoStatus);
        } catch (IllegalArgumentException e) {
            // Status inválido, pode logar ou ignorar
        }
        return "redirect:/tarefas";
    }
}
