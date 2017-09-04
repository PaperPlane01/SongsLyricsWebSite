package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 03.09.2017.
 */
public class WrongPasswordException extends Exception {

    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }

    public WrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
