package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class InvalidSongGenresException extends Exception {

    public InvalidSongGenresException() {
        super();
    }

    public InvalidSongGenresException(String message) {
        super(message);
    }

    public InvalidSongGenresException(String message, Throwable cause) {
        super(message, cause);
    }
}
