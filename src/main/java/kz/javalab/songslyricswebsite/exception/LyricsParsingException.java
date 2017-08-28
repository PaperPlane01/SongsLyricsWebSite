package kz.javalab.songslyricswebsite.exception;

/**
 * Created by PaperPlane on 28.08.2017.
 */
public class LyricsParsingException extends Exception{

    public LyricsParsingException() {
        super();
    }

    public LyricsParsingException(String message) {
        super(message);
    }

    public LyricsParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
