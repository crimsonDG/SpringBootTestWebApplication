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
public class AuthUserSender {
    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    public void sendAuthUser(KeycloakEntityDto user) throws TimeoutException, IOException {
        Channel channel = rabbitMQConfig.initChannel(RabbitMQConfig.AUTH_QUEUE);
        channel.basicPublish("", RabbitMQConfig.AUTH_QUEUE, null,  rabbitMQConfig.convertToByte(user));
        log.info(user.getUsername() + " has logged in!");
        channel.close();
    }
}
