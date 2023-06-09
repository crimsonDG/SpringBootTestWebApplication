package com.admin.rabbitmq;

import com.core.model.UserDto;
import com.rabbitmq.client.*;
import com.rabbitmq.config.RabbitMQConfig;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class RegistrationLog {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @PostConstruct
    public void processQueue() throws IOException, TimeoutException {

        Channel channel = rabbitMQConfig.initChannel(RabbitMQConfig.REGISTRATION_QUEUE);

        Consumer consumer = new DefaultConsumer(channel) {
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                ByteArrayInputStream bis = new ByteArrayInputStream(body);
                ObjectInputStream in = new ObjectInputStream(bis);
                UserDto userDto = (UserDto) in.readObject();
                log.info(userDto.getLogin() + " has registered in the system!");
            }
        };
        channel.basicConsume(RabbitMQConfig.REGISTRATION_QUEUE, true, consumer);
    }
}
