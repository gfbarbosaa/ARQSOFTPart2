package pt.psoft.g1.psoftg1.authormanagement.api.Publisher;

import org.springframework.stereotype.Component;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import pt.psoft.g1.psoftg1.authormanagement.model.messages.AuthorCreatedMessage;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorMessagePublisher;
import pt.psoft.g1.psoftg1.configuration.RabbitMQConfig;

@Component
public class RabbitMQAuthorPublisher implements AuthorMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQAuthorPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishAuthorCreated(AuthorCreatedMessage message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.AUTHOR_EXCHANGE,
                RabbitMQConfig.AUTHOR_CREATED_ROUTING_KEY,
                message
        );
    }
}
