package kz.javalab.songslyricswebsite.exception;

public class UserUnblockingException extends Exception {

    public UserUnblockingException() {
    }

    public UserUnblockingException(String message) {
        super(message);
    }

    public UserUnblockingException(String message, Throwable cause) {
        super(message, cause);
    }
}
