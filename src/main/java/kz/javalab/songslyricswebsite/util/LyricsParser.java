package kz.javalab.songslyricswebsite.util;

import kz.javalab.songslyricswebsite.entity.lyrics.Line;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.exception.LyricsParsingException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by PaperPlane on 02.08.2017.
 */
public class LyricsParser {

    private final String INTRO = "[INTRO]";
    private final String VERSE = "[VERSE]";
    private final String CHORUS = "[CHORUS]";
    private final String HOOK = "[HOOK]";
    private final String BRIDGE = "[BRIDGE]";
    private final String OUTRO = "[OUTRO]";
    private final String EMPTY_LINE = "";
    private final String CLOSING_TAG = "[/]";

    public SongLyricsComposite buildLyricsFromString(String lyricsAsText) throws LyricsParsingException {
        if (!validateLyrics(lyricsAsText)) {
            throw new LyricsParsingException();
        }

        SongLyricsComposite songLyrics = new SongLyricsComposite();
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

    private boolean isOpeningTag(String candidate) {
        boolean result = false;

        if (!CLOSING_TAG.equals(candidate)) {
            if (isTag(candidate)) {
                result = true;
            }
        }

        return result;
    }

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