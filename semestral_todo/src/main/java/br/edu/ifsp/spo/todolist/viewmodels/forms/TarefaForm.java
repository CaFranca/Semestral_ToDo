package br.edu.ifsp.spo.todolist.viewmodels.forms;

public class TarefaForm {
    private String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarefaForm that = (TarefaForm) o;
        return texto != null ? texto.equals(that.texto) : that.texto == null;
    }

    @Override
    public int hashCode() {
        return texto != null ? texto.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TarefaForm{" +
                "texto='" + texto + '\'' +
                '}';
    }
}
