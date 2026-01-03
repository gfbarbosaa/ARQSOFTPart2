package pt.psoft.g1.psoftg1.authormanagement.api.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import pt.psoft.g1.psoftg1.authormanagement.model.messages.AuthorCreatedMessage;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorServiceImpl;
import pt.psoft.g1.psoftg1.configuration.RabbitMQConfig;

@Component
public class AuthorCreatedConsumer {

    private final AuthorServiceImpl authorReadService;

    public AuthorCreatedConsumer(AuthorServiceImpl authorReadService) {
        this.authorReadService = authorReadService;
    }

    @RabbitListener(queues = RabbitMQConfig.AUTHOR_CREATED_QUEUE)
    public void consume(AuthorCreatedMessage message) {

        authorReadService.createFromEvent(
                message.authorNumber(),
                message.name(),
                message.bio(),
                message.photoUri()
        );
    }
}
