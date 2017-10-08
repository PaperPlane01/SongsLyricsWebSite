package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 08.10.2017.
 */
public class SongApprovingException extends Exception {

    public SongApprovingException() {
    }

    public SongApprovingException(String message) {
        super(message);
    }

    public SongApprovingException(String message, Throwable cause) {
        super(message, cause);
    }
}
