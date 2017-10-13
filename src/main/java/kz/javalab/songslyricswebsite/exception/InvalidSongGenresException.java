package kz.javalab.songslyricswebsite.exception;

public class InvalidSongGenresException extends Exception {

    public InvalidSongGenresException() {
        super();
    }

    public InvalidSongGenresException(String message) {
        super(message);
    }

    public InvalidSongGenresException(String message, Throwable cause) {
        super(message, cause);
    }
}
