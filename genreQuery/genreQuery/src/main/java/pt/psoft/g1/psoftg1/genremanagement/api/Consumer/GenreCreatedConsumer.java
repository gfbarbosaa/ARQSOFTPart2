package pt.psoft.g1.psoftg1.genremanagement.api.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.configuration.RabbitMQConfig;
import pt.psoft.g1.psoftg1.genremanagement.model.messages.GenreCreatedMessage;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreServiceImpl;

@Component
public class GenreCreatedConsumer {

    private final GenreServiceImpl genreService;

    public GenreCreatedConsumer(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @RabbitListener(queues = RabbitMQConfig.GENRE_CREATED_QUEUE)
    public void consume(GenreCreatedMessage message) {

        genreService.createFromEvent(
                message.genre()
        );
    }
}
