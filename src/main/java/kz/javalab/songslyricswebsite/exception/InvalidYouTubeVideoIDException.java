package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class InvalidYouTubeVideoIDException extends Exception {

    public InvalidYouTubeVideoIDException() {
        super();
    }

    public InvalidYouTubeVideoIDException(String message) {
        super(message);
    }

    public InvalidYouTubeVideoIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
