package pt.psoft.g1.psoftg1.authormanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.messages.AuthorCreatedMessage;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @org.springframework.beans.factory.annotation.Autowired
    private AuthorRepository authorRepository;
    @org.springframework.beans.factory.annotation.Autowired
    private AuthorMapper mapper;
    @org.springframework.beans.factory.annotation.Autowired
    private PhotoRepository photoRepository;
    private final AuthorMessagePublisher publisher;

    @Override
    public Author create(final CreateAuthorRequest resource) {
        /*
         * Since photos can be null (no photo uploaded) that means the URI can be null as well.
         * To avoid the client sending false data, photoURI has to be set to any value / null
         * according to the MultipartFile photo object
         *
         * That means:
         * - photo = null && photoURI = null -> photo is removed
         * - photo = null && photoURI = validString -> ignored
         * - photo = validFile && photoURI = null -> ignored
         * - photo = validFile && photoURI = validString -> photo is set
         * */

        MultipartFile photo = resource.getPhoto();
        String photoURI = resource.getPhotoURI();
        if (photo == null && photoURI != null || photo != null && photoURI == null) {
            resource.setPhoto(null);
            resource.setPhotoURI(null);
        }
        publisher.publishAuthorCreated(
                new AuthorCreatedMessage(
                        saved.getAuthorNumber(),
                        saved.getName(),
                        saved.getBio(),
                        saved.getPhoto() != null ? saved.getPhoto().getPhotoFile() : null,
                        UUID.randomUUID()
                )
        );
        final Author author = mapper.create(resource);
        return authorRepository.save(author);
    }

    @Override
    public Author partialUpdate(final Long authorNumber, final UpdateAuthorRequest request, final long desiredVersion) {
        // first let's check if the object exists so we don't create a new object with
        // save
        /*
         * Since photos can be null (no photo uploaded) that means the URI can be null as well.
         * To avoid the client sending false data, photoURI has to be set to any value / null
         * according to the MultipartFile photo object
         *
         * That means:
         * - photo = null && photoURI = null -> photo is removed
         * - photo = null && photoURI = validString -> ignored
         * - photo = validFile && photoURI = null -> ignored
         * - photo = validFile && photoURI = validString -> photo is set
         * */

        MultipartFile photo = request.getPhoto();
        String photoURI = request.getPhotoURI();
        if (photo == null && photoURI != null || photo != null && photoURI == null) {
            request.setPhoto(null);
            request.setPhotoURI(null);
        }
        // since we got the object from the database we can check the version in memory
        // and apply the patch
        author.applyPatch(desiredVersion, request);

        // in the meantime some other user might have changed this object on the
        // database, so concurrency control will still be applied when we try to save
        // this updated object
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> removeAuthorPhoto(Long authorNumber, long desiredVersion) {
        Author author = authorRepository.findByAuthorNumber(authorNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find reader"));

        String photoFile = author.getPhoto().getPhotoFile();
        author.removePhoto(desiredVersion);
        Optional<Author> updatedAuthor = Optional.of(authorRepository.save(author));
        photoRepository.deleteByPhotoFile(photoFile);
        return updatedAuthor;
    }

}
