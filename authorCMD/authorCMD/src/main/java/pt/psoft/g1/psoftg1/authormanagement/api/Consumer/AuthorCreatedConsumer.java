
@Component
public class AuthorCreatedConsumer {

    private final AuthorReadService authorReadService;

    public AuthorCreatedConsumer(AuthorReadService authorReadService) {
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
