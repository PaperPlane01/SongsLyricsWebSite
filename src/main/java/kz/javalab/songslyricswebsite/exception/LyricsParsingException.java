package kz.javalab.songslyricswebsite.exception;

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
