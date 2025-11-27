# Semestral_ToDo - Sistema de Gerenciamento de Tarefas

## 📋 Visão Geral

O **Semestral_ToDo** é uma aplicação web para gerenciamento de tarefas, inspirada em gerenciadores populares como Todoist, Microsoft To Do, Apple Reminders e TickTick. Desenvolvido como projeto semestral, esta aplicação utiliza Spring Boot, SQLite e Thymeleaf para fornecer uma solução robusta e amigável de gerenciamento de tarefas.

## ✨ Funcionalidades

- 📋 **Gerenciamento de Tarefas**: Criar, atualizar e excluir tarefas
- 🔒 **Autenticação de Usuário**: Login e registro seguros
- 🌐 **Design Responsivo**: Funciona perfeitamente em vários dispositivos
- 📅 **Agendamento de Tarefas**: Definir datas de vencimento e lembretes
- 🔄 **Interface Amigável**: UI intuitiva e fácil de usar

## 🛠️ Stack Tecnológica

- **Linguagem de Programação**: Java, HTML, CSS, JavaScript
- **Frameworks e Bibliotecas**:
  - Spring Boot
  - Thymeleaf
  - SQLite
  - Spring Security
- **Requisitos do Sistema**:
  - Java 21
  - Maven 3.9+ ou Gradle
  - IDE de sua escolha (IntelliJ IDEA, VS Code, Eclipse)

## 🚀 Instalação Rápida

### Pré-requisitos
- Java 21
- Maven 3.9+ ou Gradle
- IDE de sua escolha

### Configuração Inicial

1. **Clone o repositório**
   ```bash
   git clone https://github.com/CaFranca/Semestral_ToDo.git
   cd Semestral_ToDo
   ```

2. **Configure o banco de dados**
   - O projeto usa SQLite
   - O arquivo de banco de dados (`database.db`) será criado automaticamente no diretório raiz

3. **Execute o projeto**
   - Compile e execute usando Maven:
     ```bash
     ./mvnw spring-boot:run
     ```
   - O servidor Spring Boot será executado em: `http://localhost:8080`

## 🎯 Como Usar

### Uso Básico
A aplicação oferece uma interface web intuitiva para:

- **Registrar nova conta de usuário**
- **Fazer login no sistema**
- **Criar novas tarefas** com título e descrição
- **Visualizar lista de tarefas**
- **Editar e excluir tarefas existentes**

### Estrutura da Interface
```html
<!-- Exemplo da estrutura básica da aplicação -->
<div class="main-container">
    <div class="task-app-container">
        <!-- Seção de adição de tarefas -->
        <!-- Lista de tarefas existentes -->
    </div>
</div>
```

## 📁 Estrutura do Projeto

```
Semestral_ToDo/
├── src/
│   ├── main/
│   │   ├── java/br/edu/ifsp/spo/todolist/
│   │   │   ├── controllers/     # Controladores MVC
│   │   │   ├── models/          # Entidades e modelos
│   │   │   ├── repositories/    # Camada de acesso a dados
│   │   │   ├── services/        # Lógica de negócio
│   │   │   └── config/          # Configurações
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── templates/       # Templates Thymeleaf
│   │   └── static/              # Arquivos estáticos (CSS, JS)
│   └── test/                    # Testes unitários
├── database/                    # Configurações do banco
└── docs/                       # Documentação
```

## ⚙️ Configuração

### Arquivo application.properties
```properties
# Configurações do banco de dados SQLite
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.datasource.url=jdbc:sqlite:database/database.db

# Configurações JPA
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configurações específicas do SQLite
spring.jpa.properties.hibernate.dialect=org.hibernate.community.dialect.SQLiteDialect
```

## 👥 Equipe de Desenvolvimento

- **Bernardo de Castro Bertoldo** - [bernacastro](https://github.com/bernacastro)
- **Caique França dos Santos** - [CaFranca](https://github.com/CaFranca)
- **Mateus Hideki de Figueiredo Tamura** - [Mateus-Hideki](https://github.com/Mateus-Hideki)
- **Renan Trajano da Conceição** - [RenanTC007](https://github.com/RenanTC007)

## 📝 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

## 📊 Documentação de Negócio

### Resumo Executivo
O Semestral_ToDo é uma aplicação web de gerenciamento de tarefas desenvolvida como projeto semestral, com o objetivo de criar uma solução amigável e eficiente para organização de tarefas.

### Escopo do Projeto

#### Funcionalidades Incluídas:
- Registro e autenticação de usuários
- Criação, edição e exclusão de tarefas
- Organização e filtragem de tarefas
- Interface responsiva e intuitiva
- Tratamento básico de erros e feedback ao usuário

#### Objetivos de Negócio:
- Aumentar produtividade e eficiência dos usuários
- Fornecer uma experiência de usuário positiva
- Concluir com sucesso o projeto semestral

### Requisitos Funcionais

#### Gerenciamento de Usuários:
- Registro e criação de conta
- Login e logout seguros

#### Gerenciamento de Tarefas:
- Criação, edição e exclusão de tarefas
- Organização e filtragem por critérios diversos

### Casos de Uso Principais

1. **Registro de Usuário**:
   - Usuário acessa página de registro
   - Preenche formulário de cadastro
   - Recebe confirmação de criação de conta

2. **Gerenciamento de Tarefas**:
   - Usuário faz login na aplicação
   - Navega para página de tarefas
   - Cria, edita ou exclui tarefas
   - Filtra e organiza tarefas conforme necessidade

### Critérios de Aceitação

- ✅ Aplicação atende todos os requisitos funcionais e não-funcionais
- ✅ Aplicação testada e validada pela equipe de desenvolvimento
- ✅ Feedback positivo dos usuários
- ✅ Conclusão bem-sucedida do projeto semestral

---

## 🔧 Documentação Técnica

### Arquitetura do Sistema

#### Camadas da Aplicação:
- **Camada de Apresentação**: Templates Thymeleaf para renderização HTML
- **Camada de Aplicação**: Spring Boot para lógica de negócio e APIs RESTful
- **Camada de Dados**: SQLite para armazenamento de dados

### API Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | /tasks | Recupera lista de tarefas |
| POST | /tasks | Cria nova tarefa |
| PUT | /tasks/{id} | Atualiza tarefa existente |
| DELETE | /tasks/{id} | Exclui tarefa |

### Esquema do Banco de Dados

#### Tabela: tasks
| Coluna | Tipo | Descrição |
|--------|------|-----------|
| id | INTEGER | Chave primária |
| title | VARCHAR | Título da tarefa |
| description | TEXT | Descrição da tarefa |
| due_date | DATETIME | Data de vencimento |
| completed | BOOLEAN | Status de conclusão |

### Diretrizes de Desenvolvimento

#### Estrutura de Pacotes:
```java
br.edu.ifsp.spo.todolist/
├── controllers/    # Manipulam requisições HTTP
├── services/       # Contém lógica de negócio
├── repositories/   # Interagem com o banco de dados
├── models/         # Entidades do banco de dados
└── config/         # Configurações da aplicação
```

### Instruções de Deploy

1. **Build do Projeto**:
   ```bash
   ./mvnw clean package
   ```

2. **Executar Aplicação**:
   ```bash
   java -jar target/semestral-todo-0.0.1-SNAPSHOT.jar
   ```

3. **Acessar Aplicação**:
   - Navegue para `http://localhost:8080`

---

## 🗺️ Roadmap e Próximos Passos

### Funcionalidades Planejadas:
- [ ] Adicionar notificações para lembretes
- [ ] Melhorar interface e experiência do usuário
- [ ] Implementar compartilhamento de tarefas

### Linha do Tempo de Implementação:
- **Semanas 1-2**: Configuração do projeto e desenvolvimento inicial
- **Semanas 3-4**: Desenvolvimento das funcionalidades de gerenciamento
- **Semanas 5-6**: Integração e testes
- **Semana 7**: Revisão final e deploy

---

## 🤝 Contribuindo

### Como Contribuir:
1. Faça um fork do repositório
2. Crie um branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para o branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request


## 🐛 Suporte e Issues

- **Reportar Problemas**: Abra uma issue no repositório GitHub
- **Obter Ajuda**: Entre em contato com os mantenedores do projeto

---

**Badges:**
[![Build Status](https://travis-ci.org/CaFranca/Semestral_ToDo.svg?branch=main)](https://travis-ci.org/CaFranca/Semestral_ToDo)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Stars](https://img.shields.io/github/stars/CaFranca/Semestral_ToDo?style=social)](https://github.com/CaFranca/Semestral_ToDo)

---

*Este README foi projetado para ser abrangente e envolvente, fornecendo instruções claras e um tom acolhedor para atrair contribuidores.*
