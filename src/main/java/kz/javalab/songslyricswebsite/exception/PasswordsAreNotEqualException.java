package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 18.09.2017.
 */
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
