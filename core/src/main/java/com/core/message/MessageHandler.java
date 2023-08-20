package com.core.message;

import com.core.model.KeycloakEntityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class MessageHandler {

    @Bean
    public Consumer<KeycloakEntityDto> definedUserInfo() {
        return message -> {
            if(message.getCreatedTimestamp() == System.currentTimeMillis()){
                log.info(message.getUsername() + " has registered to the system!");
            }
            else
            {
                log.info(message.getUsername() + " is logged in!");
            }
        };
    }

    @Bean
    public Consumer<KeycloakEntityDto> defaultConsumer() {
        return message -> log.info(message.toString());
    }

}