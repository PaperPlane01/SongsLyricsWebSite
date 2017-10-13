package kz.javalab.songslyricswebsite.exception;

public class InvalidUserNameException extends Exception {

    public InvalidUserNameException() {
        super();
    }

    public InvalidUserNameException(String message) {
        super(message);
    }

    public InvalidUserNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
