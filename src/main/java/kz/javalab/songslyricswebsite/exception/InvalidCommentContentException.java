package kz.javalab.songslyricswebsite.exception;

public class InvalidCommentContentException extends Exception {

    public InvalidCommentContentException() {
    }

    public InvalidCommentContentException(String message) {
        super(message);
    }

    public InvalidCommentContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
