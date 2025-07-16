✅ 1. Organização da equipe
🔑 Itens obrigatórios para o README inicial:
Tema do projeto: Gerenciador de Tarefas

Participantes: Nome completo + Prontuário de todos

Link do repositório: Já configurado no GitHub

Instruções de setup: Versão do Java, dependências do Spring Boot, como rodar localmente, como configurar o SQLite, variáveis de ambiente, etc.

📌 Dica prática:
Crie uma issue no GitHub só para montar o README.md. Assim todos revisam.

✅ 2. Estrutura inicial do repositório
📂 Root

bash
Copiar
Editar
/projeto-gerenciador-tarefas
│   README.md
│   .gitignore
│   /backend (Spring Boot)
│   /frontend (opcional, se forem usar SPA)
│   /docs (pode ter documentos de API, fluxogramas, wireframes)
.gitignore: Inclua /target/, /build/ e arquivos locais do SQLite.

Tags de entrega: Lembre todos de sempre usar git tag no prazo.

✅ 3. Setup técnico inicial
🛠️ Tecnologias confirmadas:
Backend: Java 17+ | Spring Boot 3.x | Spring Security | Spring Data JPA | SQLite

Frontend (opcional): React ou Angular SPA

🔑 Passos sugeridos:
1. Inicializar o projeto Spring Boot

Use Spring Initializr

Dependências mínimas: Spring Web, Spring Security, Spring Data JPA, SQLite Driver

2. Configurar conexão com SQLite

application.properties:

properties
Copiar
Editar
spring.datasource.url=jdbc:sqlite:./database/tarefas.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
✅ **4. Definição do MVP 3º Bimestre
Para a primeira entrega:

 Cadastro de usuário (/register)

 Login com JWT ou sessão (/login)

 Proteção de rota (/tarefas só acessa se autenticado)

 Redirecionamento se não autenticado

Dica: Divida isso em features no GitHub Projects ou ZenHub, para cada pessoa pegar uma parte.

✅ 5. Modelo de Banco de Dados inicial
Usuário

id (PK)

nome

email

senha (hash!)

Tarefa

id (PK)

título

descrição

data_vencimento

status (ENUM: PENDENTE, FAZENDO, CONCLUIDA)

tags (relação N:N ou string separada, se for simples)

id_usuario (FK)

✅ 6. Boas práticas no repositório
✅ Cada membro deve:

Ter branch própria (feature/fulano-login)

Fazer PRs pequenos para revisão

Revisar PR dos colegas

Commit com mensagens claras: feat: adiciona autenticação básica

✅ 7. Checklist do sucesso
 Definiu quem faz o quê

 Criou issues e milestones

 README.md atualizado

 Configuração local roda em todos os PCs da equipe

 SQLite funcionando com migrations (se usar Flyway ou Liquibase)

 Frontend integrado ou mockado para testar login

✅ 8. Extras úteis
📌 Scripts para facilitar:

./mvnw spring-boot:run (para rodar)

docker-compose (opcional, se quiser facilitar setup futuro)

Teste unitário de repositórios