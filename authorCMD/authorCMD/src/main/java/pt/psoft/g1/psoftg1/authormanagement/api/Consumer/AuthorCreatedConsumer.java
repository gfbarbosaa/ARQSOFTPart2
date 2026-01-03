package pt.psoft.g1.psoftg1.authormanagement.api.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import pt.psoft.g1.psoftg1.authormanagement.model.messages.AuthorCreatedMessage;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorServiceImpl;
import pt.psoft.g1.psoftg1.configuration.RabbitMQConfig;

@Component
public class AuthorCreatedConsumer {

    private final AuthorServiceImpl authorServiceImpl;

    public AuthorCreatedConsumer(AuthorServiceImpl authorServiceImpl) {
        this.authorServiceImpl = authorServiceImpl;
    }

    @RabbitListener(queues = RabbitMQConfig.AUTHOR_CREATED_QUEUE)
    public void consume(AuthorCreatedMessage message) {

        authorServiceImpl.createFromEvent(
                message.authorNumber(),
                message.name(),
                message.bio(),
                message.photoUri()
        );
    }
}
