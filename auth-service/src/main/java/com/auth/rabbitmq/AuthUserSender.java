package com.auth.rabbitmq;

import com.core.model.UserDto;
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

    public void sendAuthUser(UserDto userDto) throws TimeoutException, IOException {
        Channel channel = rabbitMQConfig.initChannel(RabbitMQConfig.AUTH_QUEUE);
        channel.basicPublish("", RabbitMQConfig.AUTH_QUEUE, null,  rabbitMQConfig.convertToByte(userDto));
        log.info(userDto.getLogin() + " has logged in!");
        channel.close();
    }
}
