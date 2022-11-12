package io.github.emersondll.transactions.service.impl;

import io.github.emersondll.transactions.service.RabbitMqService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    @Autowired
    private RabbitTemplate template;

    public void send(String queue, Object message) {
        this.template.convertAndSend(queue, message);
    }
}
