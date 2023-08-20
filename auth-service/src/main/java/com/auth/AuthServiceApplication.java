package com.auth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"com.core.domain"})
@EnableJpaRepositories({"com.core.repository"})
@ComponentScans({
        @ComponentScan({"com.core"}),
        @ComponentScan({"com.security"}),
        @ComponentScan({"com.rabbitmq.config"}),
        @ComponentScan({"com.redis"})
})

@OpenAPIDefinition(servers = {@Server(url = "${server.servlet.context-path}")}, info = @Info(
        title = "SpringBootTestWebApplication",
        version = "1.0.0",
        description = "Auth service application"))

public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}