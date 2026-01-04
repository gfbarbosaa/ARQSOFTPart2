package pt.psoft.g1.psoftg1.bookmanagement.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.*;
import pt.psoft.g1.psoftg1.bookmanagement.model.messages.BookCreatedMessage;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource({"classpath:config/library.properties"})
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final PhotoRepository photoRepository;
    private final ReaderRepository readerRepository;
    private final BookMessagePublisher publisher;

    @Value("${suggestionsLimitPerGenre}")
    private long suggestionsLimitPerGenre;

    @Override
    public Book create(CreateBookRequest request, String isbn) {

        if (bookRepository.findByIsbn(isbn).isPresent()) {
            throw new ConflictException("Book with ISBN " + isbn + " already exists");
        }

        List<Long> authorNumbers = request.getAuthors();
        List<Author> authors = new ArrayList<>();
        for (Long authorNumber : authorNumbers) {

            Optional<Author> temp = authorRepository.findByAuthorNumber(authorNumber);
            if (temp.isEmpty()) {
                continue;
            }

            Author author = temp.get();
            authors.add(author);
        }

        MultipartFile photo = request.getPhoto();
        String photoURI = request.getPhotoURI();
        if (photo == null && photoURI != null || photo != null && photoURI == null) {
            request.setPhoto(null);
            request.setPhotoURI(null);
        }

        final var genre = genreRepository.findByString(request.getGenre())
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        Book newBook = new Book(isbn, request.getTitle(), request.getDescription(), genre, authors, photoURI);
        publisher.publishBookCreated(
                new BookCreatedMessage(
                        saved.getIsbn(),
                        saved.getTitle().toString(),
                        saved.getDescription(),
                        genre.getName(),
                        authors.stream().map(Author::getAuthorNumber).toList(),
                        saved.getPhoto() != null ? saved.getPhoto().getPhotoFile() : null,
                        UUID.randomUUID()
                )
        );

        return bookRepository.save(newBook);
    }

    @Override
    public Book update(UpdateBookRequest request, String currentVersion) {

        var book = findByIsbn(request.getIsbn());
        if (request.getAuthors() != null) {
            List<Long> authorNumbers = request.getAuthors();
            List<Author> authors = new ArrayList<>();
            for (Long authorNumber : authorNumbers) {
                Optional<Author> temp = authorRepository.findByAuthorNumber(authorNumber);
                if (temp.isEmpty()) {
                    continue;
                }
                Author author = temp.get();
                authors.add(author);
            }

            request.setAuthorObjList(authors);
        }

        MultipartFile photo = request.getPhoto();
        String photoURI = request.getPhotoURI();
        if (photo == null && photoURI != null || photo != null && photoURI == null) {
            request.setPhoto(null);
            request.setPhotoURI(null);
        }

        if (request.getGenre() != null) {
            Optional<Genre> genre = genreRepository.findByString(request.getGenre());
            if (genre.isEmpty()) {
                throw new NotFoundException("Genre not found");
            }
            request.setGenreObj(genre.get());
        }

        book.applyPatch(Long.parseLong(currentVersion), request);

        bookRepository.save(book);

        return book;
    }

    @Override
    public Book save(Book book) {
        return this.bookRepository.save(book);
    }

    public void createFromEvent(
            String isbn,
            String title,
            String description,
            String genreName,
            List<Long> authorNumbers,
            String photoUri
    ) {

        if (bookRepository.findByIsbn(isbn).isPresent()) {
            return; // idempotente
        }

        Genre genre = genreRepository.findByString(genreName)
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        List<Author> authors = authorRepository.findAllById(authorNumbers);

        Book book = new Book(
                isbn,
                title,
                description,
                genre,
                authors,
                photoUri
        );

        bookRepository.save(book);
    }
}
