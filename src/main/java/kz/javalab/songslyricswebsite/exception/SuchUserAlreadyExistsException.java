package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 26.08.2017.
 */
public class SuchUserAlreadyExistsException extends Exception {

    public SuchUserAlreadyExistsException() {
        super();
    }

    public SuchUserAlreadyExistsException(String message) {
        super(message);
    }

    public SuchUserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
