package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 27.08.2017.
 */
public class NoSuchSongException extends Exception {
    public NoSuchSongException() {
        super();
    }

    public NoSuchSongException(String message) {
        super(message);
    }

    public NoSuchSongException(String message, Throwable cause) {
        super(message, cause);
    }
}
