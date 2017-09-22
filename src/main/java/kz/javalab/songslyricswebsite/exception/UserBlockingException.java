package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 22.09.2017.
 */
public class UserBlockingException extends Exception {

    public UserBlockingException() {
    }

    public UserBlockingException(String message) {
        super(message);
    }

    public UserBlockingException(String message, Throwable cause) {
        super(message, cause);
    }
}
