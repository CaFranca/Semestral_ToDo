#  Semestral_ToDo

Aplicação para gerenciamento de tarefas, inspirada em referências como Todoist, Microsoft Todos, Apple Lembretes e TickTick.  
Desenvolvido como projeto semestral da disciplina de Programação para Web.

---

##  Integrantes da Equipe

| Nome completo                        | Matrícula  | GitHub Profile                      |
| ------------------------------------- | ---------- | ----------------------------------- |
| Bernardo de Castro Bertoldo           | SP3114775  | [bernacastro](https://github.com/bernacastro) |
| Caique França dos Santos              | SP3118541  | [CaFranca](https://github.com/CaFranca) |
| Mateus Hideki de Figueiredo Tamura    | SP3116191  | [Mateus-Hideki](https://github.com/Mateus-Hideki) |
| Renan Trajano da Conceição            | SP3115887  | [RenanTC007](https://github.com/RenanTC007) |

---

##  Repositório

[https://github.com/CaFranca/Semestral_ToDo.git](https://github.com/CaFranca/Semestral_ToDo.git)

---

##  Configuração Inicial

1. **Pré-requisitos**
   - Java 21
   - Maven 3.9+ ou Gradle (dependendo do gerador usado)
   - IDE de sua preferência (IntelliJ IDEA, VS Code ou Eclipse)

2. **Clone do projeto**
   ```bash
   git clone https://github.com/CaFranca/Semestral_ToDo.git
   cd Semestral_ToDo

3. **Configuração do banco de dados**
   - Este projeto usa SQLite.
   - O arquivo do banco (database.db) será criado automaticamente na raiz ou conforme configuração.

4. **Rodar o projeto**
- Compile e execute com sua IDE ou usando Maven:
   ```bash
        ./mvnw spring-boot:run
- O servidor Spring Boot rodará em: http://localhost:8080

Estrutura de Pastas e Pacotes
-----------------------------

Vou listar os principais pacotes e o que geralmente cada um contém no projeto:

### 1\. **br.edu.ifsp.spo.todolist**

Pacote raiz do projeto.

### 2\. **br.edu.ifsp.spo.todolist.controllers**

*   Responsabilidade:Controladores MVC que recebem requisições HTTP, fazem o intermédio entre a camada de serviço e a camada de visualização (views).
    
*   O que contém:Classes que definem os endpoints REST ou as rotas para páginas (usando Thymeleaf).
    
*   Exemplo:TarefasController.java — para manipular as requisições relacionadas às tarefas (listar, adicionar, alterar status, etc).AuthController.java — gerencia login, registro, logout.
    

### 3\. **br.edu.ifsp.spo.todolist.models**

*   Responsabilidade:Entidades JPA que representam as tabelas do banco de dados e os objetos do domínio.
    
*   O que contém:Classes anotadas com @Entity, com campos mapeados para colunas do banco.
    
*   Exemplo:User.java — entidade do usuário, implementa UserDetails para Spring Security.Tarefa.java — entidade tarefa, com atributos como texto, status, data de vencimento, tags e relacionamento com usuário.
    

### 4\. **br.edu.ifsp.spo.todolist.repositories**

*   Responsabilidade:Interfaces que estendem JpaRepository para acesso e manipulação do banco de dados.
    
*   O que contém:Métodos de consulta e persistência, métodos customizados baseados em convenções Spring Data.
    
*   Exemplo:UserRepository.java — busca usuários por email, nome, etc.TarefasRepository.java — busca tarefas por usuário, status, etc.
    

### 5\. **br.edu.ifsp.spo.todolist.services**

*   Responsabilidade:Camada de serviços com regras de negócio, lógica entre controle e persistência.
    
*   O que contém:Classes que fazem validações, orquestram chamadas aos repositórios, etc.
    
*   Exemplo:TarefasService.java — método para listar, adicionar tarefas, alterar status.UserDetailsServiceImpl.java — serviço para buscar usuário para autenticação.
    

### 6\. **br.edu.ifsp.spo.todolist.forms**

*   Responsabilidade:Classes DTO (Data Transfer Object) usadas para formular dados da UI, especialmente para captura e validação dos dados dos formulários.
    
*   O que contém:Classes que modelam os campos dos formulários (exemplo: TarefaForm com texto, tagsString, data de vencimento, status).
    

### 7\. **br.edu.ifsp.spo.todolist.config**

*   Responsabilidade:Classes de configuração do Spring Boot, principalmente para segurança e outras configurações específicas da aplicação.
    
*   O que contém:SecurityConfig.java — configura autenticação, autorização, regras de acesso, etc.
    

Outros Arquivos / Recursos
--------------------------

*   resources/templates/:Templates Thymeleaf para as páginas HTML da aplicação (exemplo: login.html, register.html, tarefas/index.html).
    
*   resources/static/:Arquivos estáticos (CSS, JS, imagens).
    
*   application.properties:Configurações gerais do Spring Boot (ex: conexão com banco, porta, etc).
    

Fluxo básico do sistema
-----------------------

1.  O usuário acessa a aplicação e é direcionado para a página de login ou registro.
    
2.  Após autenticação, ele é levado para o controller TarefasController.
    
3.  O controller chama os métodos do TarefasService.
    
4.  O serviço manipula a entidade Tarefa e usa o TarefasRepository para persistir ou recuperar dados.
    
5.  As respostas são enviadas às views Thymeleaf para renderização.
    

Resumo do padrão usado:
-----------------------

*   **Model** = Entidades JPA (models).
    
*   **View** = Thymeleaf (resources/templates).
    
*   **Controller** = Classes REST/MVC (controllers).
    
*   **Repository** = Camada de acesso a dados (repositories).
    
*   **Service** = Regras de negócio e lógica (services).
    
*   **Form** = Objetos para formulários (forms).
    
*   **Config** = Configurações (ex: segurança) (config).
