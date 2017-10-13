package kz.javalab.songslyricswebsite.exception;

public class SuchUserAlreadyExistsException extends Exception {

    public SuchUserAlreadyExistsException() {
        super();
    }

    public SuchUserAlreadyExistsException(String message) {
        super(message);
    }

    public SuchUserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
