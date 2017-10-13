package kz.javalab.songslyricswebsite.exception;

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
