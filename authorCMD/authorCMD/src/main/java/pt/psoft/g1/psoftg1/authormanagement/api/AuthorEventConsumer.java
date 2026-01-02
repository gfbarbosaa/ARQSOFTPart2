package pt.psoft.g1.psoftg1.authormanagement.api;

@Component
public class AuthorEventConsumer {

    @RabbitListener(queues = "author.created.queue")
    public void handleAuthorCreated(AuthorCreatedEvent event) {
        // atualizar read model
        // emitir log
        // sincronizar outro contexto interno
    }
}
