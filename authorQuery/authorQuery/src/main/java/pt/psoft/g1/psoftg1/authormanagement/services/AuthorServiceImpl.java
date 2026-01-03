package pt.psoft.g1.psoftg1.authormanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper mapper;
    private final PhotoRepository photoRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper mapper, PhotoRepository photoRepository) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
        this.photoRepository = photoRepository;
    }

    @Override
    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findByAuthorNumber(final Long authorNumber) {
        return authorRepository.findByAuthorNumber(authorNumber);
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        return authorRepository.findCoAuthorsByAuthorNumber(authorNumber);
    }

    @Override
    public List<Author> findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    @Override
    public List<AuthorLendingView> findTopAuthorByLendings() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findTopAuthorByLendings'");
    }

    @Override
    public Optional<Author> removeAuthorPhoto(Long authorNumber, long desiredVersion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAuthorPhoto'");
    }

    public void createFromEvent(
            Long authorNumber,
            String name,
            String bio,
            String photoUri
    ) {

        Author author = new Author();
        author.setAuthorNumber(authorNumber);
        author.setName(name);
        author.setBio(bio);

        if (photoUri != null) {
            author.addPhoto(photoUri);
        }

        authorRepository.save(author);
    }
}
