package kz.javalab.songslyricswebsite.exception;

public class SongRatingException extends Exception {

    public SongRatingException() {
    }

    public SongRatingException(String message) {
        super(message);
    }

    public SongRatingException(String message, Throwable cause) {
        super(message, cause);
    }
}
