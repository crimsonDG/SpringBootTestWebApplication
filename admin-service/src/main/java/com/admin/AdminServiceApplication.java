package com.admin;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.core.client")
@EntityScan({"com.core.domain"})
@EnableJpaRepositories({"com.core.repository"})
@ComponentScans({
        @ComponentScan({"com.core"}),
        @ComponentScan({"com.security"}),
        @ComponentScan({"com.redis"})
})

@OpenAPIDefinition(
        servers = {@Server(url = "${server.servlet.context-path}")} , info = @Info(
                title = "SpringBootTestWebApplication",
                version = "1.0.0",
                description = "Admin service application"
        )
)

@SecuritySchemes({
        @SecurityScheme(
                name = "Direct Access Grants",
                type = SecuritySchemeType.OAUTH2,
                flows = @OAuthFlows(
                        password = @OAuthFlow(
                                tokenUrl = "${KEYCLOAK_TOKEN_URL}"
                        )
                )
        ),
        @SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
})
public class AdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }
}