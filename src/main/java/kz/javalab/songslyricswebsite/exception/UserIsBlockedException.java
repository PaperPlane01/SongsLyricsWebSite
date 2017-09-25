package kz.javalab.songslyricswebsite.exception;

public class UserIsBlockedException extends Exception {

    public UserIsBlockedException() {
    }

    public UserIsBlockedException(String message) {
        super(message);
    }

    public UserIsBlockedException(String message, Throwable cause) {
        super(message, cause);
    }
}
