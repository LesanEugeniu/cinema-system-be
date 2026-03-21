package md.cineticket.cinemasystem.model;

import lombok.Getter;

@Getter
public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    HORROR("Horror"),
    THRILLER("Thriller"),
    ROMANCE("Romance"),
    SCI_FI("Sci-Fi"),
    FANTASY("Fantasy"),
    ANIMATION("Animation"),
    DOCUMENTARY("Documentary"),
    CRIME("Crime"),
    MYSTERY("Mystery"),
    FAMILY("Family");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

}
