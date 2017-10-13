package kz.javalab.songslyricswebsite.exception;

public class UserBlockingException extends Exception {

    public UserBlockingException() {
    }

    public UserBlockingException(String message) {
        super(message);
    }

    public UserBlockingException(String message, Throwable cause) {
        super(message, cause);
    }
}
