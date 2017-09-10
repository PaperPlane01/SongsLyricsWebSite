package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 09.09.2017.
 */
public class CommentAddingException extends Exception {

    public CommentAddingException() {
        super();
    }

    public CommentAddingException(String message) {
        super(message);
    }

    public CommentAddingException(String message, Throwable cause) {
        super(message, cause);
    }
}
