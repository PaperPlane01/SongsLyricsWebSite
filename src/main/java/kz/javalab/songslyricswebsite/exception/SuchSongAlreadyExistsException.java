package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 28.08.2017.
 */
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
