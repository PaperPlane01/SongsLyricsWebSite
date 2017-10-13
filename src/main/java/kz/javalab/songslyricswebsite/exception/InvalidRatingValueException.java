package kz.javalab.songslyricswebsite.exception;

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
