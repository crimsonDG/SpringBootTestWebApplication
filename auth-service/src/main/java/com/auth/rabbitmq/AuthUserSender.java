package com.auth.rabbitmq;

import com.core.model.KeycloakEntityDto;
import com.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class AuthUserSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private Consumer<KeycloakEntityDto> definedUserInfo;

    public void sendAuthUser(KeycloakEntityDto keycloakEntityDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.AUTH_ROUTING_KEY, keycloakEntityDto);
        definedUserInfo.accept(keycloakEntityDto);
    }
}
