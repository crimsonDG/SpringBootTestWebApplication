package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String AUTH_QUEUE = "authQueue";
    public static final String EXCHANGE = "exchange";
    public static final String AUTH_ROUTING_KEY = "authRoutingKey";
    public static final String REGISTRATION_QUEUE = "registrationQueue";
    public static final String REGISTRATION_ROUTING_KEY = "registrationRoutingKey";

    @Value("${SPRING_RABBITMQ_HOST}")
    private String SPRING_RABBITMQ_HOST;

    @Value("${SPRING_RABBITMQ_PORT}")
    private int SPRING_RABBITMQ_PORT;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setPort(SPRING_RABBITMQ_PORT);
        connectionFactory.setHost(SPRING_RABBITMQ_HOST);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public Queue authQueue() {
        return new Queue(AUTH_QUEUE);
    }

    @Bean
    public Queue registrationQueue() {
        return new Queue(REGISTRATION_QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding authBinding(@Qualifier(AUTH_QUEUE) Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(AUTH_ROUTING_KEY);
    }

    @Bean
    public Binding registrationBinding(@Qualifier(REGISTRATION_QUEUE) Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(REGISTRATION_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
