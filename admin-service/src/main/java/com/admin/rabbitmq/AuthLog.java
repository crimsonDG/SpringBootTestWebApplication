package com.admin.rabbitmq;

import com.core.model.KeycloakEntityDto;
import com.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class AuthLog {

    @Autowired
    private Consumer<KeycloakEntityDto> definedUserInfo;

    @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE)
    public void authReceiver(KeycloakEntityDto keycloakEntityDto) {
        definedUserInfo.accept(keycloakEntityDto);
    }

}
