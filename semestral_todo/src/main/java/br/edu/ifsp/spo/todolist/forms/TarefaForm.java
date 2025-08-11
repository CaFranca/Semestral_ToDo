package br.edu.ifsp.spo.todolist.forms;

import java.time.LocalDate;
import br.edu.ifsp.spo.todolist.models.Tarefa.Status;

public class TarefaForm {
    private String texto;
    private LocalDate dataVencimento;
    private Status status;
    private String tagsString;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTagsString() {
        return tagsString;
    }

    public void setTagsString(String tagsString) {
        this.tagsString = tagsString;
    }
}
