package pt.psoft.g1.psoftg1.bookmanagement.services;

import pt.psoft.g1.psoftg1.bookmanagement.model.messages.BookCreatedMessage;

public interface BookMessagePublisher {

    void publishBookCreated(BookCreatedMessage message);
}
