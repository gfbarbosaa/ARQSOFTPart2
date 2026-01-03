package pt.psoft.g1.psoftg1.authormanagement.model.messages;

import java.util.UUID;

public record AuthorCreatedMessage(
        Long authorNumber,
        String name,
        String bio,
        String photoUri,
        UUID correlationId
        ) {

}
