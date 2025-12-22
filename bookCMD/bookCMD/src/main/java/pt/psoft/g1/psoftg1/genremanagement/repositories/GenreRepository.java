package pt.psoft.g1.psoftg1.genremanagement.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);
}
