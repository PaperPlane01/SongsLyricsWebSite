package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 30.08.2017.
 */
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
