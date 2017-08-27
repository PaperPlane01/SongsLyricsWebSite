package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 26.08.2017.
 */
public class InvalidPasswordException extends Exception {

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
