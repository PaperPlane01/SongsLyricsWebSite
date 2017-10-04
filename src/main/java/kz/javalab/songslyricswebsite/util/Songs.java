package kz.javalab.songslyricswebsite.util;

import kz.javalab.songslyricswebsite.entity.lyrics.Line;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.entity.song.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * This class consists of static utility methods for operating on <Code>Song</Code> objects.
 */
public class Songs {

    private Songs() {
    }

    /**
     * Returns number of lines containing in song.
     * @param song Song to be examined.
     * @return Number of lines containing in song.
     */
    public static int getNumberOfLines(Song song) {
        return getLines(song).size();
    }

    /**
     * Returns list of lines containing in song.
     * @param song Song to be examined.
     * @return List of lines containing in song.
     */
    public static List<Line> getLines(Song song) {
        List<Line> lines = new ArrayList<>();

        for (SongLyrics songPart : song.getLyrics().getComponents()) {
            for (SongLyrics line : songPart.getComponents()) {
                lines.add((Line) line);
            }
        }

        return lines;
    }

    /**
     * Returns type of lyrics part which contains line with specified position.
     * @param song Song to be examined.
     * @param linePosition Position of the line.
     * @return Type of lyrics part which contains line with specified position.
     */
    public static SongLyricsPartType getTypeOfLyricsPartWhichContainsLine(Song song, int linePosition) {

        int currentLinePosition = 0;

        SongLyricsPartType songLyricsPartType = null;
        List<SongLyrics> lyricsParts = song.getLyrics().getComponents();

        for (int songPartPosition = 0; songPartPosition < lyricsParts.size(); songPartPosition++) {
            List<SongLyrics> linesOfLyricsPart = lyricsParts.get(songPartPosition).getComponents();
            for (int linePositionInLyricsPart = 0; linePositionInLyricsPart < linesOfLyricsPart.size(); linePositionInLyricsPart++) {
                if (currentLinePosition == linePosition) {
                    songLyricsPartType = lyricsParts.get(songPartPosition).getType();
                }
                currentLinePosition++;
            }
        }

        return songLyricsPartType;
    }

    /**
     * Returns line at specified position.
     * @param song Song to be examined.
     * @param linePosition Position of the line.
     * @return Returns line at specified position.
     */
    public static Line getLineAt(Song song, int linePosition) {
        return getLines(song).get(linePosition);
    }

}
