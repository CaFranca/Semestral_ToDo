package br.edu.ifsp.spo.todolist.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Esta anotação indica que esta classe é uma entidade JPA
@Entity
//A anotação @Table indica o nome da tabela no banco de dados que será mapeada para esta entidade
@Table(name = "tarefas")
public class Tarefa {

    //A anotação @Id indica que este campo é a chave primária da entidade
    @Id
    //A anotação @GeneratedValue indica que o valor deste campo será gerado automaticamente pelo banco de dados
    //A estratégia GenerationType.IDENTITY indica que o banco de dados irá gerar o valor da chave
    //primária de forma autoincremental, ou seja, a cada nova inserção
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;
    private Boolean concluida;    

    //Este método alterna o status de conclusão da tarefa
    public boolean alterarStatus() {
        this.concluida = !this.concluida;
        return this.concluida;
    }


    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", concluida=" + concluida +
                '}';
    }

    // Método estático para construir uma nova tarefa com texto e status de conclusão (Design pattern: Fábrica)
    // Faço uso deste método para não ser necessária a exposição da propriedade "Texto" através de um método setter
    // e assim manter a imutabilidade do objeto Tarefa, na medida do possível.
    public static Tarefa construirCom(String texto, boolean concluida) {
        Tarefa tarefa = new Tarefa();
        tarefa.texto = texto;
        tarefa.concluida = concluida;
        return tarefa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tarefa tarefa = (Tarefa) o;
        return id == tarefa.id &&
                concluida == tarefa.concluida &&
                (texto != null ? texto.equals(tarefa.texto) : tarefa.texto == null);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + (texto != null ? texto.hashCode() : 0);
        result = 31 * result + Boolean.hashCode(concluida);
        return result;
    }

    // O Construtor padrão é necessário para o JPA
    public Tarefa() {
        
    }

    public Tarefa(Long id, String texto, boolean concluida) {
        this.id = id;
        this.texto = texto;
        this.concluida = concluida;
    }

    public long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isConcluida() {
        return concluida;
    }
}
