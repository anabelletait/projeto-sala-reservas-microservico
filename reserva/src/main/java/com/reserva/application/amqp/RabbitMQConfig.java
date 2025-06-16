package com.reserva.application.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue reservaCriacaoQueue() {
        return new Queue("reserva.criacao", true); // true = durável
    }
}
