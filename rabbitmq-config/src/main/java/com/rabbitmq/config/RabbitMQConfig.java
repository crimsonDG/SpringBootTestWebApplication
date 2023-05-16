package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    //Fields for auth-service and admin-service communication
    public static final String AUTH_QUEUE = "authQueue";
    public static final String EXCHANGE = "exchange";
    public static final String AUTH_ROUTING_KEY = "authRoutingKey";
    public static final String REGISTRATION_QUEUE = "registrationQueue";
    public static final String REGISTRATION_ROUTING_KEY = "registrationRoutingKey";

    @Bean
    public Queue authQueue() {
        return new Queue(AUTH_QUEUE);
    }

    @Bean
    public Queue registrationQueue() {
        return new Queue(REGISTRATION_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding authBinding(@Qualifier(AUTH_QUEUE) Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(AUTH_ROUTING_KEY);
    }

    @Bean
    public Binding registrationBinding(@Qualifier(REGISTRATION_QUEUE) Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(REGISTRATION_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
