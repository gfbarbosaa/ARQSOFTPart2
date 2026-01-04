package pt.psoft.g1.psoftg1.bookmanagement.api.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.bookmanagement.model.messages.BookCreatedMessage;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookServiceImpl;
import pt.psoft.g1.psoftg1.configuration.RabbitMQConfig;

@Component
public class BookCreatedConsumer {

    private final BookServiceImpl bookService;

    public BookCreatedConsumer(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @RabbitListener(queues = RabbitMQConfig.BOOK_CREATED_QUEUE)
    public void consume(BookCreatedMessage message) {

        bookService.createFromEvent(
                message.isbn(),
                message.title(),
                message.description(),
                message.genre(),
                message.authorNumbers(),
                message.photoUri()
        );
    }
}
