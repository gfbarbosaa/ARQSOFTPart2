package pt.psoft.g1.psoftg1.bookmanagement.api.Publisher;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.bookmanagement.model.messages.BookCreatedMessage;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookMessagePublisher;
import pt.psoft.g1.psoftg1.configuration.RabbitMQConfig;

@Component
public class RabbitMQBookPublisher implements BookMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQBookPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishBookCreated(BookCreatedMessage message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BOOK_EXCHANGE,
                RabbitMQConfig.BOOK_CREATED_ROUTING_KEY,
                message
        );
    }
}
