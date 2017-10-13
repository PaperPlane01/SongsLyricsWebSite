package kz.javalab.songslyricswebsite.exception;

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
