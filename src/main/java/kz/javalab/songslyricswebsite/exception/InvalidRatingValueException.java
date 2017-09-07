package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 07.09.2017.
 */
public class InvalidRatingValueException extends Exception {

    public InvalidRatingValueException() {
    }

    public InvalidRatingValueException(String message) {
        super(message);
    }

    public InvalidRatingValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
