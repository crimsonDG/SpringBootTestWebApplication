package com.music;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"com.music.domain"})
@EnableJpaRepositories({"com.music.repository"})
@ComponentScans({
        @ComponentScan({"com.core.exception"}),
        @ComponentScan({"com.security.config.basic"}),
        @ComponentScan({"com.redis"})
})

@OpenAPIDefinition(
        servers = {@Server(url = "${server.servlet.context-path}")}, info = @Info(
        title = "SpringBootTestWebApplication",
        version = "1.0.0",
        description = "Music service application"
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
public class MusicServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicServiceApplication.class, args);
    }
}
