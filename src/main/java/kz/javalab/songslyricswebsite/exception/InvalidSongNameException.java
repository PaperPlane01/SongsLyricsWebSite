package kz.javalab.songslyricswebsite.exception;

public class InvalidSongNameException extends Exception {

    public InvalidSongNameException() {
    }

    public InvalidSongNameException(String message) {
        super(message);
    }

    public InvalidSongNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
