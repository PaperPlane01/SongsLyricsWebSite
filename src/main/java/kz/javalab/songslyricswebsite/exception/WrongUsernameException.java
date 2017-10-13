package kz.javalab.songslyricswebsite.exception;

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
