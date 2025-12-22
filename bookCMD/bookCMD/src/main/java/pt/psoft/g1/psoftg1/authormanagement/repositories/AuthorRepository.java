package pt.psoft.g1.psoftg1.authormanagement.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);

    void delete(Author author);

}
