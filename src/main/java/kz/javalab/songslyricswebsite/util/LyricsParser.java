package kz.javalab.songslyricswebsite.util;

import kz.javalab.songslyricswebsite.entity.lyrics.Line;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.exception.LyricsParsingException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains methods for validating, parsing and unparsing <Code>SongLyrics</Code> object.
 * <Code>SongLyrics</Code> object can be represented as string. In string representation of <Code>SongLyrics</Code>
 * object, each component (lyrics part) is located within opening and closing tags. The opening tag is different for
 * each type of lyrics part, for instance, "[INTRO}" is for intro, "[CHORUS]" is for chorus, and so on. The closing tag "[/]"
 * is the same for all types of lyrics part.
 * Example of <Code>SongLyrics</Code> object converted to string:
 * [VERSE]
 * On my way to your house, sneakers in the snow
 * I only wanna see her
 * And my thoughts are so loud, 'cause I just wanna know
 * Are you gonna keep her?
 * [/]
 * [BRIDGE]
 * Only call you faded
 * Sorry, I know you hate it
 * Probably shouldn't say this
 * But it hurts to bite my tongue
 * [/]
 */
public class LyricsParser {

    /**
     * Tag for intro.
     */
    private final String INTRO = "[INTRO]";
    /**
     * Tag for verse.
     */
    private final String VERSE = "[VERSE]";
    /**
     * Tag for chorus.
     */
    private final String CHORUS = "[CHORUS]";
    /**
     * Tag for hook.
     */
    private final String HOOK = "[HOOK]";
    /**
     * Tag for bridge.
     */
    private final String BRIDGE = "[BRIDGE]";
    /**
     * Tag for outro.
     */
    private final String OUTRO = "[OUTRO]";
    /**
     * Tag for other.
     */
    private final String OTHER = "[OTHER]";
    /**
     * Empty line.
     */
    private final String EMPTY_LINE = "";
    /**
     * Closing tag.
     */
    private final String CLOSING_TAG = "[/]";

    public LyricsParser() {
    }

    /**
     * Parses song lyrics from string.
     * @param lyricsAsText String to be parsed.
     * @return Parsed song lyrics.
     * @throws LyricsParsingException Thrown if string is invalid.
     */
    public SongLyrics parseLyrics(String lyricsAsText) throws LyricsParsingException {
        if (!validateLyrics(lyricsAsText)) {
            throw new LyricsParsingException();
        }

        SongLyrics songLyrics = new SongLyricsComposite();
        List<String> lines = Arrays.asList(lyricsAsText.split("\n"));

        SongLyricsComposite lyricsPart = new SongLyricsComposite();

        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            String line = lines.get(lineIndex);

            switch (line.trim()) {
                case INTRO:
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.INTRO);
                    break;
                case VERSE:
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.VERSE);
                    break;
                case CHORUS:
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.CHORUS);
                    break;
                case HOOK:
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.HOOK);
                    break;
                case BRIDGE:
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.BRIDGE);
                    break;
                case OUTRO:
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.OUTRO);
                    break;
                case OTHER:
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.OTHER);
                    break;
                case CLOSING_TAG:
                    songLyrics.add(lyricsPart);
                    lyricsPart = null;
                    break;
                case EMPTY_LINE:
                    break;
                default:
                    if (lyricsPart != null) {
                        lyricsPart.add(new Line(line));
                    }
                    break;
            }
        }

        return songLyrics;
    }

    /**
     * Converts <Code>SongLyrics</Code> object to string.
     * @param songLyrics <Code>SongLyrics</Code> object which is to be converted to string..
     * @return <Code>SongLyrics</Code> object converted to string.
     */
    public String unparseLyrics(SongLyrics songLyrics) {
        StringBuilder stringBuilder = new StringBuilder();

        for (SongLyrics lyricsPart : songLyrics.getComponents()) {
            switch (lyricsPart.getType()) {
                case HOOK:
                    stringBuilder.append(HOOK);
                    stringBuilder.append("\n");
                    break;
                case INTRO:
                    stringBuilder.append(INTRO);
                    stringBuilder.append("\n");
                    break;
                case OUTRO:
                    stringBuilder.append(OUTRO);
                    stringBuilder.append("\n");
                    break;
                case VERSE:
                    stringBuilder.append(VERSE);
                    stringBuilder.append("\n");
                    break;
                case BRIDGE:
                    stringBuilder.append(BRIDGE);
                    stringBuilder.append("\n");
                    break;
                case CHORUS:
                    stringBuilder.append(CHORUS);
                    stringBuilder.append("\n");
                    break;
                case OTHER:
                    stringBuilder.append(OTHER);
                    stringBuilder.append("\n");
                    break;
                case LINE:
                    stringBuilder.append(lyricsPart.toString());
                    stringBuilder.append("\n");
                    break;
                default: break;
            }

            for (SongLyrics line : lyricsPart.getComponents()) {
                stringBuilder.append(line.toString());
                stringBuilder.append("\n");
            }

            stringBuilder.append(CLOSING_TAG);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();

    }

    /**
     * Validates song lyrics represented as string.
     * @param lyricsAsText Song lyrics to be validated.
     * @return <Code>True</Code> if lyrics are valid, <Code>False</Code> if not.
     */
    private boolean validateLyrics(String lyricsAsText) {
        int numberOfOpeningTags = 0;
        int numberOfClosingTags = 0;
        int numberOfNestedTags = 0;

        List<String> lines = Arrays.asList(lyricsAsText.split("\n"));

        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            String line = lines.get(lineIndex);

            if (!line.isEmpty()) {
                if (Character.toString(line.charAt(0)).equals("[")) {
                    if (!isTag(line)) {
                        return false;
                    }
                }
            }

            String nextLine = new String();

            if (lineIndex + 1 != lines.size()) {
                nextLine = lines.get(lineIndex + 1);
            }

            switch (line.trim()) {
                case INTRO:
                    numberOfOpeningTags++;
                    if (!nextLine.isEmpty()) {
                        if (Character.toString(line.charAt(0)).equals("[")) {
                            if (isOpeningTag((nextLine))) {
                                numberOfNestedTags++;
                            }
                        }}
                    break;
                case VERSE:
                    numberOfOpeningTags++;
                    if (!nextLine.isEmpty()) {
                        if (Character.toString(line.charAt(0)).equals("[")) {
                            if (isOpeningTag((nextLine))) {
                                numberOfNestedTags++;
                            }
                        }}
                    break;
                case CHORUS:
                    numberOfOpeningTags++;
                    if (!nextLine.isEmpty()) {
                        if (Character.toString(line.charAt(0)).equals("[")) {
                            if (isOpeningTag((nextLine))) {
                                numberOfNestedTags++;
                            }
                        }}
                    break;
                case HOOK:
                    numberOfOpeningTags++;
                    if (!nextLine.isEmpty()) {
                        if (Character.toString(line.charAt(0)).equals("[")) {
                            if (isOpeningTag((nextLine))) {
                                numberOfNestedTags++;
                            }
                        }}
                    break;
                case BRIDGE:
                    numberOfOpeningTags++;
                    if (!nextLine.isEmpty()) {
                        if (Character.toString(line.charAt(0)).equals("[")) {
                            if (isOpeningTag((nextLine))) {
                                numberOfNestedTags++;
                            }
                        }}
                    break;
                case OUTRO:
                    numberOfOpeningTags++;
                    if (!nextLine.isEmpty()) {
                        if (Character.toString(line.charAt(0)).equals("[")) {
                            if (isOpeningTag((nextLine))) {
                                numberOfNestedTags++;
                            }
                        }}
                    break;
                case CLOSING_TAG:
                    numberOfClosingTags++;
                    break;
            }
        }

        if (numberOfClosingTags != numberOfOpeningTags) {
            return false;
        }

        if (numberOfNestedTags != 0) {
            return false;
        }

        return true;
    }

    /**
     * Defines if string is opening tag.
     * @param candidate String to be checked.
     * @return <Code>True</Code> if string is opening tag, <Code>False</Code> if not.
     */
    private boolean isOpeningTag(String candidate) {
        boolean result = false;

        if (!CLOSING_TAG.equals(candidate)) {
            if (isTag(candidate)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Defines if string is tag.
     * @param candidate String to be checked.
     * @return <Code>True</Code> if string is tag, <Code>False</Code> if not.
     */
    private boolean isTag(String candidate) {
        if (INTRO.equals(candidate)) return true;
        if (VERSE.equals(candidate)) return true;
        if (CHORUS.equals(candidate)) return true;
        if (HOOK.equals(candidate)) return true;
        if (BRIDGE.equals(candidate)) return true;
        if (OUTRO.equals(candidate)) return true;
        if (CLOSING_TAG.equals(candidate)) return true;
        return false;
    }

}