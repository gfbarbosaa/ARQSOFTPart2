package pt.psoft.g1.psoftg1.authormanagement.api.Publisher;

@Component
@Profile("!test")
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
