package kz.javalab.songslyricswebsite.exception;

public class PasswordsAreNotEqualException extends Exception {

    public PasswordsAreNotEqualException() {
    }

    public PasswordsAreNotEqualException(String message) {
        super(message);
    }

    public PasswordsAreNotEqualException(String message, Throwable cause) {
        super(message, cause);
    }
}
