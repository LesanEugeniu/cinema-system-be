package md.cineticket.cinemasystem.model;

import lombok.Getter;

@Getter
public enum FormatType {
    TWO_D("2D"),
    THREE_D("3D");

    private final String value;

    FormatType(String value) {
        this.value = value;
    }

}