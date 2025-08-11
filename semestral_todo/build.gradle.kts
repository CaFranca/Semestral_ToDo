plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "br.edu.ifsp.spo"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		// Define a versão do Java a ser usada no projeto
		languageVersion = JavaLanguageVersion.of(21)
	}


repositories {
	// Repositório Maven Central, onde as dependências do projeto serão buscadas
	// O Maven Central é o repositório padrão para bibliotecas Java e contém uma vasta
	// coleção de bibliotecas e frameworks populares, incluindo o Spring Boot e suas dependências.
	// O Gradle irá buscar as dependências necessárias para o projeto neste repositório.
	// Isso permite que o Gradle resolva e baixe automaticamente as dependências especificadas
	// no arquivo build.gradle.kts, facilitando o gerenciamento de dependências do projeto.
	mavenCentral()
}

dependencies {
	// Dependência para o desenvolvimento de aplicações web com Spring Boot
	// Esta dependência inclui o suporte a Thymeleaf, um mecanismo de template para renderizar páginas HTML
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	// Dependência para o desenvolvimento de aplicações web com Spring Boot
	// Esta dependência inclui o suporte a Spring MVC, que é o framework para construir aplicações
	// web baseadas em Java, além de fornecer suporte a RESTful APIs
	// e outras funcionalidades essenciais para o desenvolvimento web
	implementation("org.springframework.boot:spring-boot-starter-web")
	// Dependência que adiciona recursos de monitoramento e gerenciamento para aplicações Spring Boot
	// Esta dependência inclui o Spring Boot Actuator, que fornece endpoints para monitorar e
	// gerenciar a aplicação, como informações sobre o estado da aplicação, métricas, saúde,
	// e outras informações úteis para desenvolvedores e administradores. O plugin do Spring para o VSCode 
	//faz uso deste recurso para fornecer informações sobre a aplicação em execução.
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Dependência para a persistência de dados com JPA para um projeto Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	// Dependência para adicionar os dialetos do Hibernate, para permitir que as instruções SQL sejam executadas corretamente
	// em bancos de dados como SQLite, PostgreSQL, MySQL, etc.
	implementation("org.hibernate.orm:hibernate-community-dialects:6.6.18.Final")
	// Driver JDBC para permitir a conexão com o banco de dados SQLite
	implementation("org.xerial:sqlite-jdbc:3.50.2.0")

	implementation("org.springframework.boot:spring-boot-starter-security")


	// Dependencia que fornece ferramentas de desenvolvimento para o Spring Boot
	// Esta dependência inclui o Spring Boot DevTools, que oferece recursos como recarga automática
	// de classes, reinicialização rápida da aplicação, e outras funcionalidades que facilitam o
	// desenvolvimento e depuração de aplicações Spring Boot. É especialmente útil durante o desenvolvimento,
	// pois permite que as alterações no código sejam refletidas imediatamente na aplicação em execução,
	// sem a necessidade de reiniciar o servidor manualmente.
	// Esta dependência é marcada como "developmentOnly", o que significa que ela só será
	// incluída no classpath durante o desenvolvimento e não será empacotada na versão final da aplicação.
	// Isso é útil para evitar que ferramentas de desenvolvimento sejam incluídas na versão
	// de produção da aplicação, mantendo o tamanho do pacote final menor e evitando dependências desnecessárias
	// em ambientes de produção.
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	
	// Dependência para o suporte a testes com JUnit 5 no Spring Boot
	// Esta dependência inclui o JUnit Jupiter, que é a nova API de testes do JUnit 5,
	// além de outras bibliotecas necessárias para escrever e executar testes no contexto
	// de aplicações Spring Boot. Ela permite que você escreva testes unitários e de integração
	// para suas aplicações Spring Boot, utilizando as anotações e funcionalidades do JUnit 5.
	// A dependência "testImplementation" indica que esta biblioteca será usada apenas durante
	// o desenvolvimento e execução de testes, e não será incluída no classpath da aplicação
	// em produção. Isso é útil para manter o tamanho do pacote final da aplicação menor e
	// evitar dependências desnecessárias em ambientes de produção.
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
}
