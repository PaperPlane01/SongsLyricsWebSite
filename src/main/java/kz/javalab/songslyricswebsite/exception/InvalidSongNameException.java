package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class InvalidSongNameException extends Exception {

    public InvalidSongNameException() {
    }

    public InvalidSongNameException(String message) {
        super(message);
    }

    public InvalidSongNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
