package kz.javalab.songslyricswebsite.exception;

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

