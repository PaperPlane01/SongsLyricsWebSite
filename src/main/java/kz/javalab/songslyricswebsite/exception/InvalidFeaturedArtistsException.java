package kz.javalab.songslyricswebsite.exception;

public class InvalidFeaturedArtistsException extends Exception {

    public InvalidFeaturedArtistsException() {
    }

    public InvalidFeaturedArtistsException(String message) {
        super(message);
    }

    public InvalidFeaturedArtistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
