package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 18.09.2017.
 */
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
