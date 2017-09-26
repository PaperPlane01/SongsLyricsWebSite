package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 25.09.2017.
 */
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
