package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 30.08.2017.
 */
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
