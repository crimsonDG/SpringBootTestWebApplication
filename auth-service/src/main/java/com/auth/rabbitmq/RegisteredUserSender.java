package com.auth.rabbitmq;

import com.core.model.KeycloakEntityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class RegisteredUserSender {
    @Autowired
    private StreamBridge streamBridge;

    @Value("${rabbitmq.destination.registration}")
    private String destination;

    public void sendRegisteredUser(KeycloakEntityDto keycloakEntityDto) {
        streamBridge.send(destination, keycloakEntityDto);
        log.info(keycloakEntityDto.getUsername() + " has registered to the system!");
    }
}