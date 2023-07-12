package com.auth.rabbitmq;

import com.core.model.KeycloakEntityDto;
import com.rabbitmq.client.Channel;
import com.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class RegisteredUserSender {
    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    public void sendRegisteredUser(KeycloakEntityDto user) throws TimeoutException, IOException {
        Channel channel = rabbitMQConfig.initChannel(RabbitMQConfig.REGISTRATION_QUEUE);
        channel.basicPublish("", RabbitMQConfig.REGISTRATION_QUEUE, null,  rabbitMQConfig.convertToByte(user));
        log.info(user.getUsername() + " has registered in the system!");
        channel.close();
    }
}
