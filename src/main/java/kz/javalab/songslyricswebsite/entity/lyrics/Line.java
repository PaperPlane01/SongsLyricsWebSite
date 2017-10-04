package kz.javalab.songslyricswebsite.entity.lyrics;

import java.util.List;
import java.util.Objects;

/**
 * This class represents line of the lyrics.
 * The instance of <Code>Line</Code> is a leaf for composite structure of song lyrics.
 */
public class Line implements SongLyrics {

    /**
     * Content of the line.
     */
    private String content;

    /**
     * Type of this song lyrics part. It is common for all <Code>Line</Code> instances.
     */
    private static final SongLyricsPartType TYPE = SongLyricsPartType.LINE;

    /**
     * Constructs a <Code>Line</Code> instance.
     */
    public Line() {
    }

    /**
     * Constructs <Code>Line</Code> instance with pre-defined content.
     * @param content Content of the line.
     */
    public Line(String content) {
        this.content = content;
    }

    /**
     * This method is unsupported for <Code>Line</Code> instances.
     * <Code>Line</Code> instance is a leaf of composite structure, and therefore no components
     * can be added to it.
     * @throws UnsupportedOperationException Thrown if this method is called.
     */
    @Override
    public void add(SongLyrics component) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns type of this lyrics part. It is the same for all <Code>Line</Code> instances
     * and equals to  SongLyricsPartType.LINE.
     * @return type of this lyrics part. It is the same for all <Code>Line</Code> instances
     * and equals to  SongLyricsPartType.LINE.
     */
    @Override
    public SongLyricsPartType getType() {
        return TYPE;
    }

    /**
     * Unsupported operation. <Code>Line</Code> instance is a leaf of composite structure, and therefore
     * it doesn't have any components.
     * @throws UnsupportedOperationException Thrown if this method is called.
     */
    @Override
    public List<SongLyrics> getComponents() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates string representation of <Code>Line</Code> instance.
     * @return String representation of <Code>Line</Code> instance.
     */
    @Override
    public String toString() {
        return content;
    }

    /**
     * Checks the equality of this <Code>Line</Code> instance and other object.
     * @param object Object which is to be compared with this <Code>Line</Code> instance.
     * @return <Code>True</Code> if this <Code>Line</Code> instance equals to object,
     * <code>False</code> if not.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Line line = (Line) object;
        return Objects.equals(content, line.content);
    }

    /**
     * Calculates hashcode of <Code>Line</Code> instance.
     * @return Hashcode of <Code>Line</Code> instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
