package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class SongAlteringException extends Exception {

    public SongAlteringException() {
        super();
    }

    public SongAlteringException(String message) {
        super(message);
    }

    public SongAlteringException(String message, Throwable cause) {
        super(message, cause);
    }
}
