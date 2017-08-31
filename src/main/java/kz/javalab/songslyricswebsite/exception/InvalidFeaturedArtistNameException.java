package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 30.08.2017.
 */
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
