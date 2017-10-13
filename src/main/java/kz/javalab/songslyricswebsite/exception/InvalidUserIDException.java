package kz.javalab.songslyricswebsite.exception;

public class InvalidUserIDException extends Exception {

    public InvalidUserIDException() {
        super();
    }

    public InvalidUserIDException(String message) {
        super(message);
    }

    public InvalidUserIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
