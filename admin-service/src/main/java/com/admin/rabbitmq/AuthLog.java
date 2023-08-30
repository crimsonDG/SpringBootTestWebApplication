package com.admin.rabbitmq;

import com.core.model.KeycloakEntityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class AuthLog {
    @Bean
    public Consumer<KeycloakEntityDto> authReceiver() {
        return message -> log.info(message.getUsername() + " is logged in!");
    }
}
