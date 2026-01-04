package pt.psoft.g1.psoftg1.bookmanagement.model.messages;

import java.util.List;
import java.util.UUID;

public record BookCreatedMessage(
        String isbn,
        String title,
        String description,
        String genre,
        List<Long> authorNumbers,
        String photoUri,
        UUID correlationId
) {
}
