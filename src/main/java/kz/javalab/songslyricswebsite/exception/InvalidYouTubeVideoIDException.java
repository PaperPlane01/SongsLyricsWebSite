package kz.javalab.songslyricswebsite.exception;

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
