package kz.javalab.songslyricswebsite.exception;

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
