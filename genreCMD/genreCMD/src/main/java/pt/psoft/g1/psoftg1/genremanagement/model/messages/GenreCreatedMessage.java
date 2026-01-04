package pt.psoft.g1.psoftg1.genremanagement.model.messages;

import java.util.UUID;

public record GenreCreatedMessage(
        String genre,
        UUID correlationId
        ) {

}
