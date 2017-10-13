package kz.javalab.songslyricswebsite.exception;

public class SuchSongAlreadyExistsException extends Exception {

    public SuchSongAlreadyExistsException() {
        super();
    }

    public SuchSongAlreadyExistsException(String message) {
        super(message);
    }

    public SuchSongAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
