package io.github.emersondll.transactions.service;

public interface RabbitMqService {

    void send(final String queue, final Object message);


}
