package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 26.08.2017.
 */
public class SongAddingException extends Exception {

    public SongAddingException() {
        super();
    }

    public SongAddingException(String message) {
        super(message);
    }

    public SongAddingException(String message, Throwable cause) {
        super(message, cause);
    }
}
