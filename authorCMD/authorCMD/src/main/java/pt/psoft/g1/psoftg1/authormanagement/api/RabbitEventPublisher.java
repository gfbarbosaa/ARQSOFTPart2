package pt.psoft.g1.psoftg1.authormanagement.api;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import lombok.RequiredArgsConstructor;

// Make sure DomainEventPublisher exists and is imported
// import pt.psoft.g1.psoftg1.authormanagement.api.DomainEventPublisher;
@Component
@RequiredArgsConstructor
public class RabbitEventPublisher implements DomainEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Object event) {
        rabbitTemplate.convertAndSend(
                "author.exchange",
                event.getClass().getSimpleName(),
                event
        );
    }
}
