

````markdown
# Entrega do 3º Bimestre — Semestral_ToDo

## O que será avaliado

1. Implementação de cadastro de usuários  
2. Autenticação / Login  
3. Proteção de páginas para usuários autenticados  
4. Redirecionamento automático entre login e página de tarefas  
5. Estrutura de pastas (controllers, services, repositories, models, templates)  
6. Arquivos estáticos bem organizados (css, js)  
7. Integração Thymeleaf com Spring Boot  
8. Logout funcional  
9. Correção de erros de template (ex: formatação de data)  
10. Mensagens e tratamento de erros básicos  

---

## Responsabilidades da equipe

| Nome completo                        | Matrícula  | GitHub       | Atividades realizadas / Sendo implementadas |
|---------------------------------------|------------|--------------|---------------------------------------------|
| Bernardo de Castro Bertoldo           | SP3114775  | bernacastro  | Trabalhando na adição da função para editar as tarefas |
| Caique França dos Santos              | SP3118541  | CaFranca     | Responsável pela implementação da base da aplicação: cadastro de usuários, autenticação/login, proteção de páginas autenticadas, redirecionamentos, estrutura de pacotes (controllers, services, repositories, models, templates), organização de arquivos estáticos (CSS/JS), integração com Thymeleaf e configuração de logout funcional |
| Mateus Hideki de Figueiredo Tamura    | SP3116191  | Mateus-Hideki| Trabalhando nas páginas de erro personalizadas, substituindo as páginas padrão do Spring por versões customizadas e mais amigáveis |
| Renan Trajano da Conceição            | SP3115887  | RenanTC007   | Adicionou a função que permite deletar tarefas, além de mover o filtro para a lateral da página para melhor usabilidade |


---

## Como rodar a versão da entrega

1. Certifique-se de usar Java 21.  
2. Compile e execute com Maven:  
   ```bash
   ./mvnw spring-boot:run
````

3. Acesse `http://localhost:8080/`.
4. Se não estiver logado, será redirecionado para `login`.
5. Após login, vá para `/tarefas`, página de tarefas protegida.

---

## Observações sobre esta versão

* A funcionalidade de filtragem por data e tags foi parcialmente implementada.
* A formatação de data foi ajustada para usar `#temporals.format(...)` no Thymeleaf.
* Alguns estilos CSS ou temas podem estar incompletos.
* Falta testes automatizados e tratamento de exceções mais robusto.

---

