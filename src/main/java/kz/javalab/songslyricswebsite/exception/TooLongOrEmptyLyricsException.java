package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class TooLongOrEmptyLyricsException extends Exception {

    public TooLongOrEmptyLyricsException() {
    }

    public TooLongOrEmptyLyricsException(String message) {
        super(message);
    }

    public TooLongOrEmptyLyricsException(String message, Throwable cause) {
        super(message, cause);
    }
}

