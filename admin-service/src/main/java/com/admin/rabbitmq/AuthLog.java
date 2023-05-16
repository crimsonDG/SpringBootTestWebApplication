package com.admin.rabbitmq;

import com.core.model.UserDto;
import com.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthLog {

    @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE)
    public void authReceiver(UserDto userDto) {
        log.info(userDto.getLogin() + " has logged in!");
    }

}
