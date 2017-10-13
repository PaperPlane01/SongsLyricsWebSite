package kz.javalab.songslyricswebsite.exception;

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
