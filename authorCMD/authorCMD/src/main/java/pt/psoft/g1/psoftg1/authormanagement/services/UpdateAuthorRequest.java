package pt.psoft.g1.psoftg1.authormanagement.services;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthorRequest {

    @Size(max = 4096)
    private String bio;

    @Size(max = 150)
    private String name;

    @Nullable
    @Getter
    @Setter
    private MultipartFile photo;

    @Nullable
    @Getter
    @Setter
    private String photoURI;

    String getPhotoURI() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void setPhoto(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void setPhotoURI(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    MultipartFile getPhoto() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object bio() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bio'");
    }

    public Object photoURI() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'photoURI'");
    }
}
