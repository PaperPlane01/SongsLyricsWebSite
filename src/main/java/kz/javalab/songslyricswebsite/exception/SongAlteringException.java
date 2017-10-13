package kz.javalab.songslyricswebsite.exception;

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
