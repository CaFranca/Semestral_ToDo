package br.edu.ifsp.spo.todolist.controllers;

// Define o pacote onde a classe está localizada
// Importa anotações do Spring necessárias para criar um controller
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

final class Tarefa {
    private final String texto;
    private final boolean concluida;

    Tarefa(String texto, boolean concluida) {
        this.texto = texto;
        this.concluida = concluida;
    }

    public String texto() {
        return texto;
    }

    public boolean concluida() {
        return concluida;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Tarefa) obj;
        return Objects.equals(this.texto, that.texto) &&
                this.concluida == that.concluida;
    }

    @Override
    public int hashCode() {
        return Objects.hash(texto, concluida);
    }

    @Override
    public String toString() {
        return "Tarefa[" +
                "texto=" + texto + ", " +
                "concluida=" + concluida + ']';
    }
}

@Controller
public class HomeController {

    List<Tarefa> tarefas = new ArrayList<>(Arrays.asList(
            new Tarefa("Comprar pão", false),
            new Tarefa("Comprar batata", true),
            new Tarefa("Estudar para a prova de SPOLBP2", false)
    ));

    @GetMapping("/")
    public String index(Model model,@RequestParam(name = "filtro", required = false, defaultValue = "todas")
    String filtro) {
        List<Tarefa> filtradas;
        switch (filtro.toLowerCase()) {
            case "concluidas":
            case "concluidos":
                filtradas = tarefas.stream()
                        .filter(Tarefa::concluida)
                        .toList();
                break;
            case "pendentes":
                filtradas = tarefas.stream()
                        .filter(t -> !t.concluida())
                        .toList();
                break;
            default:
                filtradas = new ArrayList<>(tarefas);
        }

        model.addAttribute("tarefas", filtradas);
        model.addAttribute("filtro", filtro);
        return "tarefas/index.html";
    }



    // NOVO ENDPOINT para alternar o status
    @GetMapping("/concluir/{index}")
    public String concluir(@PathVariable int index) {
        if (index >= 0 && index < tarefas.size()) {
            Tarefa atual = tarefas.get(index);
            tarefas.set(index, new Tarefa(atual.texto(), !atual.concluida()));
        }
        return "redirect:/";
    }

    @PostMapping("/add")
    public String adicionar(@RequestParam("texto") String texto) {
        if (texto != null && !texto.trim().isEmpty()) {
            tarefas.add(new Tarefa(texto.trim(), false));
        }
        return "redirect:/";
    }
}
