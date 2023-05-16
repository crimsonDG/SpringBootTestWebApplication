package com.admin.rabbitmq;

import com.core.model.UserDto;
import com.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegistrationLog {

    @RabbitListener(queues = RabbitMQConfig.REGISTRATION_QUEUE)
    public void registrationReceiver(UserDto userDto) {
        log.info(userDto.getLogin() + " has registered in the system!");
    }
}
