package br.edu.ifsp.spo.todolist.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    public enum Status {
        PENDENTE,
        FAZENDO,
        CONCLUIDA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate dataVencimento;

    // Usando Set para tags simples armazenadas como strings (pode ajustar para entidade Tag depois)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tarefas_tags", joinColumns = @JoinColumn(name = "tarefa_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    @ManyToOne(optional = false)  // indica que não pode ser nulo
    @JoinColumn(name = "user_id")
    private User user;

    // getter e setter
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }



    public Tarefa() {
        this.status = Status.PENDENTE;  // status padrão
    }

    public Tarefa(Long id, String texto, Status status, LocalDate dataVencimento, Set<String> tags) {
        this.id = id;
        this.texto = texto;
        this.status = status;
        this.dataVencimento = dataVencimento;
        this.tags = tags != null ? tags : new HashSet<>();
    }

    public static Tarefa construirCom(String texto, Status status, LocalDate dataVencimento, Set<String> tags) {
        Tarefa tarefa = new Tarefa();
        tarefa.texto = texto;
        tarefa.status = status != null ? status : Status.PENDENTE;
        tarefa.dataVencimento = dataVencimento;
        tarefa.tags = tags != null ? tags : new HashSet<>();
        return tarefa;
    }

    public void alterarStatus(Status novoStatus) {
        this.status = novoStatus;
    }

    // getters e setters

    public Long getId() { return id; }

    public String getTexto() { return texto; }

    public void setTexto(String texto) { this.texto = texto; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public LocalDate getDataVencimento() { return dataVencimento; }

    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }

    public Set<String> getTags() { return tags; }

    public void setTags(Set<String> tags) { this.tags = tags; }

    public boolean isVencida() {
        return this.dataVencimento != null && this.dataVencimento.isBefore(LocalDate.now());
    }


    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", status=" + status +
                ", dataVencimento=" + dataVencimento +
                ", tags=" + tags +
                '}';
    }
}
