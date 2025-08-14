package br.edu.ifsp.spo.todolist.controllers;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.edu.ifsp.spo.todolist.models.Tarefa.Status;
import br.edu.ifsp.spo.todolist.models.User;
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
    public String index(
            Model model,
            @RequestParam(required = false, defaultValue = "todas") String filtroStatus,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false, defaultValue = "asc") String ordem,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        var tarefas = service.listar(filtroStatus, tag, dataInicio, dataFim, ordem, usuarioLogado);

        // Pega todas as tags existentes (não duplicadas)
        Set<String> tagsExistentes = service.listarTagsExistentes();

        model.addAttribute("tarefas", tarefas);
        model.addAttribute("tarefaForm", new TarefaForm());
        model.addAttribute("filtroStatus", filtroStatus);
        model.addAttribute("tag", tag);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        model.addAttribute("ordem", ordem);
        model.addAttribute("tagsExistentes", tagsExistentes); // <- use este nome no HTML

        return "tarefas/index.html";
    }



    @PostMapping("")
    public String create(
            @ModelAttribute TarefaForm tarefaForm,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        if (tarefaForm.getTexto() == null || tarefaForm.getTexto().isBlank()) {
            return "redirect:/tarefas";
        }

        Set<String> tags = Set.of();
        if (tarefaForm.getTagsString() != null && !tarefaForm.getTagsString().isBlank()) {
            tags = Arrays.stream(tarefaForm.getTagsString().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
        }

        service.adicionarNovaTarefa(
                tarefaForm.getTexto(),
                tarefaForm.getDataVencimento(),
                tarefaForm.getStatus() != null ? tarefaForm.getStatus() : Status.PENDENTE,
                tags,
                usuarioLogado // agora vinculando ao usuário
        );

        return "redirect:/tarefas";
    }

    @GetMapping("/{id}/marcar-como/{status}")
    public String marcarComo(
            @PathVariable Long id,
            @PathVariable String status,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        try {
            Status novoStatus = Status.valueOf(status.toUpperCase());
            service.alterarStatus(id, novoStatus, usuarioLogado);
        } catch (IllegalArgumentException e) {
            // status inválido
        }
        return "redirect:/tarefas";
    }
}

