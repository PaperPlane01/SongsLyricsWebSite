package kz.javalab.songslyricswebsite.exception;

public class InvalidFeaturedArtistNameException extends Exception {

    public InvalidFeaturedArtistNameException() {
    }

    public InvalidFeaturedArtistNameException(String message) {
        super(message);
    }

    public InvalidFeaturedArtistNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
