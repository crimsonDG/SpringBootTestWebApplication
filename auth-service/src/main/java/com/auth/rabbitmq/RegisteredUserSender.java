package com.auth.rabbitmq;

import com.core.model.UserDto;
import com.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegisteredUserSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendRegisteredUser(UserDto userDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.REGISTRATION_ROUTING_KEY, userDto);
        log.info(userDto.getLogin() + " has registered in the system!");
    }
}
