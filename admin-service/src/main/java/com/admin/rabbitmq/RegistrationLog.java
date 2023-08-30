package com.admin.rabbitmq;

import com.core.model.KeycloakEntityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class RegistrationLog {

    @Bean
    public Consumer<KeycloakEntityDto> registrationReceiver() {
        return message -> log.info(message.getUsername() + " has registered to the system!");
    }
}
