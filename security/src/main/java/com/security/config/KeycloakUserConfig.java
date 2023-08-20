package com.security.config;

import com.core.client.AuthFeignClient;
import com.core.repository.KeycloakCredentialRepository;
import com.core.repository.KeycloakEntityRepository;
import com.core.repository.KeycloakRoleRepository;
import com.redis.service.RedissonService;
import com.security.common.KeycloakValues;
import com.security.service.KeycloakUserService;
import com.security.service.MusicProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KeycloakUserConfig {

    @Autowired(required = false)
    private AuthFeignClient authFeignClient;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedissonService redissonService;

    @Autowired
    private KeycloakEntityRepository keycloakEntityRepository;

    @Autowired
    private KeycloakRoleRepository keycloakRoleRepository;

    @Autowired
    private KeycloakCredentialRepository keycloakCredentialRepository;

    @Bean
    public KeycloakUserService keycloakUserService() {
        return new KeycloakUserService(authFeignClient, modelMapper, keycloakEntityRepository, keycloakRoleRepository, keycloakCredentialRepository, keycloakValues());
    }

    @Bean
    public MusicProfileService musicProfileService() {
        return new MusicProfileService(redissonService, modelMapper);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public KeycloakValues keycloakValues() {
        return new KeycloakValues(
                "ddd02889-9b44-4fc6-a5f6-161af2d8d02d",
                "demo",
                "{\"hashIterations\":27500,\"algorithm\":\"pbkdf2-sha256\",\"additionalParameters\":{}}",
                "password",
                "custom",
                "http://keycloak:8080/realms/demo/protocol/openid-connect/token",
                "demo-client");
    }
}
