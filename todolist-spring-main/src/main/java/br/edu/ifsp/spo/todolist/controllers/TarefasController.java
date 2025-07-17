//package br.edu.ifsp.spo.todolist.controllers;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//record Tarefa(String texto, boolean concluida) { }
//
//@Controller
//public class TarefasController {
//
//    List<Tarefa> tarefas = new ArrayList<>(Arrays.asList(
//            new Tarefa("Comprar pão", false),
//            new Tarefa("Comprar batata", true),
//            new Tarefa("Estudar para a prova de SPOLBP2", false)
//    ));
//
//    @GetMapping("/tarefas")
//    public String index(Model model){
//
//        model.addAttribute("tarefas", this.tarefas);
//
//        return "tarefas/index.html";
//    }
//}
