package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 03.09.2017.
 */
public class WrongUsernameException extends Exception {

    public WrongUsernameException() {
        super();
    }

    public WrongUsernameException(String message) {
        super(message);
    }

    public WrongUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
