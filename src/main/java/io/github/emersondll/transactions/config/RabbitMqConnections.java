package io.github.emersondll.transactions.config;

import io.github.emersondll.transactions.config.constants.RabbitMqConstants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class RabbitMqConnections {
    private static final String EXCHANGE_NAME = "amq.direct";
    private final AmqpAdmin amqpAdmin;

    public RabbitMqConnections(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding binding(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void init() {
        Queue queueWithdrawal = this.queue(RabbitMqConstants.WITHDRAWAL);
        Queue queueDeposit = this.queue(RabbitMqConstants.DEPOSIT);
        Queue queueAccount = this.queue(RabbitMqConstants.ACCOUNT);
        DirectExchange exchange = this.directExchange();
        Binding bindingDeposit = this.binding(queueDeposit, exchange);
        Binding bindingWithdrawal = this.binding(queueWithdrawal, exchange);
        Binding bindingAccount = this.binding(queueAccount, exchange);
        this.amqpAdmin.declareQueue(queueWithdrawal);
        this.amqpAdmin.declareQueue(queueDeposit);
        this.amqpAdmin.declareQueue(queueAccount);
        this.amqpAdmin.declareExchange(exchange);
        this.amqpAdmin.declareBinding(bindingDeposit);
        this.amqpAdmin.declareBinding(bindingWithdrawal);
        this.amqpAdmin.declareBinding(bindingAccount);


    }

}
