package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 22.09.2017.
 */
public class NoPermissionException extends Exception {

    public NoPermissionException() {
    }

    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
