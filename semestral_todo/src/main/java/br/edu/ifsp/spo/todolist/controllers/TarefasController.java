package br.edu.ifsp.spo.todolist.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;


import br.edu.ifsp.spo.todolist.models.Tarefa;
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

        String statusFilterForQuery = filtroStatus;
        if ("concluidas".equals(filtroStatus)) {
            statusFilterForQuery = "concluida"; // Convert plural to singular
        }

        var tarefas = service.listar(filtroStatus, tag, dataInicio, dataFim, ordem, usuarioLogado);
        tarefas.sort(
                Comparator.comparing(
                        Tarefa::getDataVencimento,
                        Comparator.nullsLast(Comparator.naturalOrder())
                )
        );



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
            // Handle both singular and plural forms
            String statusValue = status;
            if ("concluidas".equalsIgnoreCase(status)) {
                statusValue = "concluida";
            }
            Status novoStatus = Status.valueOf(statusValue.toUpperCase());
            service.alterarStatus(id, novoStatus, usuarioLogado);
        } catch (IllegalArgumentException e) {
            // Handle invalid status
        }
        return "redirect:/tarefas";
    }

    @GetMapping("/{id}/deletar-tarefa")
    public String deletarTarefa(
            @PathVariable Long id,
            @AuthenticationPrincipal User usuarioLogado
    ){
        try {
            service.apagarTarefa(id, usuarioLogado);

        }catch (IllegalArgumentException e){
            // Handle invalid status
        }
        return "redirect:/tarefas";
    }

    @GetMapping("/{id}/editar-tarefa")
    public String mostrarPaginaEdicao (
            @PathVariable Long id, Model model,
            @AuthenticationPrincipal User usuarioLogado
    )
    {
        Tarefa tarefa_edicao = service.buscarTarefaEdicao(id, usuarioLogado); // encontra a tarefa a ser editada
        model.addAttribute("tarefa", tarefa_edicao); // faz com que as infos da tarefa vão pro html

        return "EdicaoTarefas";
    }

    @PostMapping("/{id}/editar-tarefa")
    public String salvarEdicao (
            @PathVariable Long id,
            @ModelAttribute Tarefa tarefa,
            @AuthenticationPrincipal User usuarioLogado
    )
    {
        service.editarTarefa(id, tarefa, usuarioLogado);

        return "redirect:/tarefas";
    }
}

