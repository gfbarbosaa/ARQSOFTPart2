package pt.psoft.g1.psoftg1.genremanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Table
public class Genre {

    @Transient
    private final int GENRE_MAX_LENGTH = 100;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long pk;

    protected Genre() {
    }

    public Genre(long pk) {
        this.pk = pk;
    }
}
