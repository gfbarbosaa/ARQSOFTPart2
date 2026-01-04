package pt.psoft.g1.psoftg1.genremanagement.services;


import pt.psoft.g1.psoftg1.genremanagement.model.messages.GenreCreatedMessage;

public interface GenreMessagePublisher {

    void publishGenreCreated(GenreCreatedMessage message);
}
