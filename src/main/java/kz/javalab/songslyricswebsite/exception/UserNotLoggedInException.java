package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 18.09.2017.
 */
public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException() {
    }

    public UserNotLoggedInException(String message) {
        super(message);
    }

    public UserNotLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }
}
