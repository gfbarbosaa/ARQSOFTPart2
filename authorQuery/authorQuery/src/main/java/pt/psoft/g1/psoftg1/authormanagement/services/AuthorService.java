package pt.psoft.g1.psoftg1.authormanagement.services;

import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface AuthorService {

    Iterable<Author> findAll();

    Optional<Author> findByAuthorNumber(Long authorNumber);

    List<Author> findByName(String name);

    List<AuthorLendingView> findTopAuthorByLendings();

    List<Author> findCoAuthorsByAuthorNumber(Long authorNumber);

    Optional<Author> removeAuthorPhoto(Long authorNumber, long desiredVersion);
}
