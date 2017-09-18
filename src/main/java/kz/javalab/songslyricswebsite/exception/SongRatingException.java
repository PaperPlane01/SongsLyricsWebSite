package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 18.09.2017.
 */
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
