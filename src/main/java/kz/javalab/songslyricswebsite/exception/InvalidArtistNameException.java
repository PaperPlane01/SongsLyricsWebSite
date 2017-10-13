package kz.javalab.songslyricswebsite.exception;

public class InvalidArtistNameException extends Exception {

    public InvalidArtistNameException() {
    }

    public InvalidArtistNameException(String message) {
        super(message);
    }

    public InvalidArtistNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
