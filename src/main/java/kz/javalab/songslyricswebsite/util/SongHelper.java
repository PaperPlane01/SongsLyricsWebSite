package kz.javalab.songslyricswebsite.util;

import kz.javalab.songslyricswebsite.entity.lyrics.Line;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.entity.song.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class SongHelper {

    private SongHelper() {
    }

    public static int getNumberOfLines(Song song) {
        return getLines(song).size();
    }

    public static List<Line> getLines(Song song) {
        List<Line> lines = new ArrayList<>();

        song.getLyrics().getComponents().forEach(songPart -> {
            songPart.getComponents().forEach(line -> {
                lines.add((Line) line);
            });
        });

        return lines;
    }

    public static SongLyricsPartType getTypeOfLyricsPartWhichContainsLine(Song song, int linePosition) {

        int currentLinePosition = 0;

        SongLyricsPartType songLyricsPartType = null;

        for (int songPartPosition = 0; songPartPosition <song.getLyrics().getComponents().size(); songPartPosition++) {
            for (int linePositionInLyricsPart = 0; linePositionInLyricsPart < song.getLyrics().getComponents().get(songPartPosition).getComponents().size(); linePositionInLyricsPart++) {
                if (currentLinePosition == linePosition) {
                    songLyricsPartType = song.getLyrics().getComponents().get(songPartPosition).getType();
                }
                currentLinePosition++;
            }
        }

        return songLyricsPartType;
    }

    public static Line getLineAt(Song song, int linePosition) {
        return getLines(song).get(linePosition);
    }

}
