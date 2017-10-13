package kz.javalab.songslyricswebsite.exception;

public class PasswordChangingException extends Exception {

    public PasswordChangingException() {
    }

    public PasswordChangingException(String message) {
        super(message);
    }

    public PasswordChangingException(String message, Throwable cause) {
        super(message, cause);
    }
}
