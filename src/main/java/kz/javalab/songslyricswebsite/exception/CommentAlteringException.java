package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 09.09.2017.
 */
public class CommentAlteringException extends Exception {

    public CommentAlteringException() {
    }

    public CommentAlteringException(String message) {
        super(message);
    }

    public CommentAlteringException(String message, Throwable cause) {
        super(message, cause);
    }
}
