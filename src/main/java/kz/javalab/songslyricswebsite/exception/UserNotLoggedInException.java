package kz.javalab.songslyricswebsite.exception;

public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException() {
    }

    public UserNotLoggedInException(String message) {
        super(message);
    }

    public UserNotLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }
}
