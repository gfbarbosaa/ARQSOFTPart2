package pt.psoft.g1.psoftg1.authormanagement.services;

import pt.psoft.g1.psoftg1.authormanagement.model.messages.AuthorCreatedMessage;

public interface AuthorMessagePublisher {

    void publishAuthorCreated(AuthorCreatedMessage message);
}
