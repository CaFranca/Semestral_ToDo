package br.edu.ifsp.spo.todolist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifsp.spo.todolist.services.TarefasService;
import br.edu.ifsp.spo.todolist.viewmodels.forms.TarefaForm;

@Controller
//Esta anotação indica que todas as rotas aqui definidas terão o prefixo "/tarefas"
@RequestMapping("/tarefas")
public class TarefasController {

    private final TarefasService service;

    public TarefasController(TarefasService service) {
        this.service = service;
    }

    //Aqui temos um exemplo de rota que irá responder a requisições GET para a URL "/tarefas"
    //Esta rota possui um parâmetro opcional chamado "filtro"
    //Se o parâmetro "filtro" não for fornecido, ele terá o valor padrão de uma string vazia
    //O parâmetro "filtro" pode ser usado para filtrar as tarefas exibidas na página
    //Por exemplo, você pode passar "pendentes" para exibir apenas as tarefas pendentes
    //ou "concluidas" para exibir apenas as tarefas concluídas
    //O método index irá buscar as tarefas no serviço e adicioná-las ao modelo
    //Por exemplo, se você acessar a URL "/tarefas?filtro=pendentes",
    //a página exibirá apenas as tarefas que estão pendentes
    @GetMapping("")
    public String index(Model model, @RequestParam(required = false, defaultValue = "") String filtro) {

        var tarefas = this.service.listar(filtro);

        model.addAttribute("tarefas", tarefas);
        model.addAttribute("tarefaForm", new TarefaForm());

        return "tarefas/index.html";
    }

    @PostMapping("")
    // O Spring MVC irá automaticamente preencher o objeto tarefaForm com os dados do formulário
    // A anotação @ModelAttribute é opcional aqui, pois o Spring já reconhece
    // que o parâmetro tarefaForm deve ser preenchido com os dados do formulário
    // Se você quiser usar @ModelAttribute, você pode descomentar a linha abaixo
    // Isto é útil se você quiser adicionar configurações adicionais ao mapeamento
    // public String create(Model model, @ModelAttribute("tarefaForm") TarefaForm tarefaForm){
    public String create(Model model, TarefaForm tarefaForm) {
        if (tarefaForm.getTexto() != null && !tarefaForm.getTexto().isBlank())
            this.service.adicionarNovaTarefa(tarefaForm.getTexto());

        //Exemplo de redirecionamento para a rota "index"
        return "redirect:/tarefas";
    }

    //Este exemplo mostra como criar uma rota que tenha um parâmetro incorporado na URL
    //por exemplo, a rota "/tarefas/1/marcar-como-concluida" irá chamar este método com o parâmetro id = 1
    @GetMapping("/{id}/marcar-como-concluida")
    public String marcarComoConcluida(@PathVariable long id) {
        this.service.marcarComoConcluida(id);
        return "redirect:/tarefas";
    }

    @GetMapping("/{id}/marcar-como-nao-concluida")
    public String marcarComoNaoConcluida(@PathVariable long id) {
        this.service.marcarComoNaoConcluida(id);
        return "redirect:/tarefas";
    }
}
