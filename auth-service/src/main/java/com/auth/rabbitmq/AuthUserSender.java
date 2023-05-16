package com.auth.rabbitmq;

import com.core.model.UserDto;
import com.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthUserSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendAuthUser(UserDto userDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.AUTH_ROUTING_KEY, userDto);
        log.info(userDto.getLogin() + " has logged in!");
    }
}
