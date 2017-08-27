package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 26.08.2017.
 */
public class InvalidUserNameException extends Exception {

    public InvalidUserNameException() {
        super();
    }

    public InvalidUserNameException(String message) {
        super(message);
    }

    public InvalidUserNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
