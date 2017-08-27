package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 26.08.2017.
 */
public class RegistrationFailedException extends Exception {

    public RegistrationFailedException() {
    }

    public RegistrationFailedException(String message) {
        super(message);
    }

    public RegistrationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
