package br.edu.ifsp.spo.todolist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifsp.spo.todolist.models.Tarefa;

//Aqui é feita a declaração de um repositório
@Repository
//Para que o repositório funcione a partir de uma entidade JPA, é necessário estender a interface JpaRepository
//A interface JpaRepository é uma interface do Spring Data JPA que fornece métodos para operações CRUD
//O primeiro parâmetro é a entidade que o repositório irá gerenciar, no caso, a entidade Tarefa
//O segundo parâmetro é o tipo do identificador da entidade, que é Long no caso da entidade Tarefa
//Com isso, o Spring Data JPA irá gerar a implementação do repositório automaticamente
//Você pode adicionar métodos personalizados aqui, seguindo o padrão de nomenclatura do Spring Data JPA
//Por exemplo, você pode criar métodos para buscar tarefas por status, por texto, etc.
//Esses métodos serão implementados automaticamente pelo Spring Data JPA
public interface TarefasRepository extends JpaRepository<Tarefa, Long> {
    
    //Esta assinatura de método permite buscar tarefas com base no status de conclusão
    //O Spring Data JPA irá gerar a implementação automaticamente
    //O nome do método deve seguir o padrão findBy + nome do atributo 
    //(o parâmetro é o atributo que será usado para a busca, no caso, a propriedada "concluida")
    //O tipo de retorno é uma lista de Tarefa, pois pode haver várias tarefas com o mesmo status
    //O parâmetro boolean concluida indica se estamos buscando tarefas concluídas ou não concluídas
    //Exemplo de uso: findByConcluida(true) para buscar tarefas concluídas
    //Exemplo de uso: findByConcluida(false) para buscar tarefas não concluídas
    List<Tarefa> findByConcluida(boolean concluida);



}