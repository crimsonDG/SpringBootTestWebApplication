package com.gateway.config;


import com.gateway.token.JwtAuthConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebFluxSecurity
@Configuration
public class GatewaySecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/webjars/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/admin/v3/api-docs/**",
            "/auth/v3/api-docs/**",
            "/main/v3/api-docs/**",
            "/music/v3/api-docs/**"
    };

    private final JwtAuthConverter jwtAuthConverter;

    public GatewaySecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**").permitAll() // Eureka Discovery Access
                        .pathMatchers("/actuator/info").permitAll()// Management descriptions Access
                        .pathMatchers("/actuator/health/**").permitAll()
                        .pathMatchers("/sba/**").permitAll() // Spring Boot Admin Access
                        .pathMatchers(SWAGGER_WHITELIST).permitAll()
                        .pathMatchers("/auth/keycloak/**").permitAll()
                        .pathMatchers("/admin/keycloak/**").hasAnyRole("ADMIN")
                        .pathMatchers("/admin/profile/**").hasAnyRole("ADMIN")
                        .pathMatchers("/music/songs/**").hasAnyRole("ADMIN")
                        .pathMatchers("/music/labels/**").hasAnyRole("ADMIN")
                        .pathMatchers("/main/keycloak/**").hasAnyRole("USER")
                        .anyExchange()
                        .authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));
        return serverHttpSecurity.build();
    }
}