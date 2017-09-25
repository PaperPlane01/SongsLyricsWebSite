package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 24.09.2017.
 */
public class UserUnblockingException extends Exception {

    public UserUnblockingException() {
    }

    public UserUnblockingException(String message) {
        super(message);
    }

    public UserUnblockingException(String message, Throwable cause) {
        super(message, cause);
    }
}
