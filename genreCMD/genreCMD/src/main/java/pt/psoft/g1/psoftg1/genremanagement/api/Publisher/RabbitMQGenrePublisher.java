package pt.psoft.g1.psoftg1.genremanagement.api.Publisher;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.configuration.RabbitMQConfig;
import pt.psoft.g1.psoftg1.genremanagement.model.messages.GenreCreatedMessage;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreMessagePublisher;

@Component
public class RabbitMQGenrePublisher implements GenreMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQGenrePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishGenreCreated(GenreCreatedMessage message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.GENRE_EXCHANGE,
                RabbitMQConfig.GENRE_CREATED_ROUTING_KEY,
                message
        );
    }
}
