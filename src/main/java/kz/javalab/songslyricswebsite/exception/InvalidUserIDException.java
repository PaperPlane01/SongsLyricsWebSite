package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 26.08.2017.
 */
public class InvalidUserIDException extends Exception {

    public InvalidUserIDException() {
        super();
    }

    public InvalidUserIDException(String message) {
        super(message);
    }

    public InvalidUserIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
