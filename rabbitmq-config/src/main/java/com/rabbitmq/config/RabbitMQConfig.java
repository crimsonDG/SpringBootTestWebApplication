package com.rabbitmq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMQConfig {
    //Fields for auth-service and admin-service communication
    public static final String AUTH_QUEUE = "authQueue";
    public static final String REGISTRATION_QUEUE = "registrationQueue";

    @Value("${SPRING_RABBITMQ_HOST}")
    private String SPRING_RABBITMQ_HOST;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setPort(5672);
        connectionFactory.setHost(SPRING_RABBITMQ_HOST);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public org.springframework.amqp.rabbit.connection.Connection connection() throws IOException, TimeoutException {
        return connectionFactory().createConnection();
    }

    public Channel initChannel(String queue) throws IOException, TimeoutException {
        Channel channel = connection().createChannel(false);

        channel.queueDeclare(queue, true, false, false, null);

        return channel;
    }

    public byte[] convertToByte(Object user) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(user);
        out.flush();

        return bos.toByteArray();
    }
}
