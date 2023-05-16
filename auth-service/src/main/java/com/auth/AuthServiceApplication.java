package com.auth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({"com.core.domain"})
@EnableJpaRepositories({"com.core.repository"})
@ComponentScans({
        @ComponentScan({"com.core"}),
        @ComponentScan({"com.security"}),
        @ComponentScan({"com.rabbitmq.config"})
})
@OpenAPIDefinition(info = @Info(title = "SpringBootTestWebApplication", version = "1.0.0", description = "Auth service application"))
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}