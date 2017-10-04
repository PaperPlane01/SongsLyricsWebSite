package kz.javalab.songslyricswebsite.entity.lyrics;

import java.util.List;

/**
 * Instances of <Code>SongLyrics</Code> interface can represent lyrics of the song, parts of lyrics and lines.
 * It is conceived that <Code>SongLyrics</Code> instance has a composite structure with maximum 3 levels.
 * The fist level is lyrics of the song itself.
 * The second level is part of the lyrics (Verse, Chorus, Bridge, etc.)
 * The third (leaf) level is line.
 * The first two levels are optional.
 */
public interface SongLyrics {

    /**
     * Adds new component to <Code>SongLyrics</Code> instance.
     * @param component Component to be added.
     */
    void add(SongLyrics component);

    /**
     * Returns type of <Code>SongLyrics</Code> instance.
     * @return Type of <Code>SongLyrics</Code> instance.
     */
    SongLyricsPartType getType();

    /**
     * Returns components of <Code>SongLyrics</Code> instance.
     * @return Type of <Code>SongLyrics</Code> instance.
     */
    List<SongLyrics> getComponents();
}
