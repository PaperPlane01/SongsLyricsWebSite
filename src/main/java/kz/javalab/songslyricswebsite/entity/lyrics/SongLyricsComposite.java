package kz.javalab.songslyricswebsite.entity.lyrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <Code>SongLyricsComposite</Code> instance can represent part of lyrics of the song or whole lyrics of the song.
 * In first case, <Code>SongLyricsComposite</Code> instance consists of <Code>Line</Code> objects.
 * In second, <Code>SongLyricsComposite</Code> instance consists of <Code>SongLyricsComposite</Code> objects which
 * represent parts of song's lyrics.
 */
public class SongLyricsComposite implements SongLyrics {

    /**
     * Components of the <Code>SongLyricsComposite</Code> instance.
     */
    private List<SongLyrics> components = new ArrayList<>();
    /**
     * Type of the <Code>SongLyricsComposite</Code> instance.
     */
    private SongLyricsPartType type;

    /**
     * Constructs <Code>SongLyricsComposite</Code> instance.
     */
    public SongLyricsComposite() {
    }

    /**
     * Constructs <Code>SongLyricsComposite</Code> instance with pre-defined list of components
     * and type.
     * @param components List of components.
     * @param type Type of the instance.
     */
    public SongLyricsComposite(List<SongLyrics> components, SongLyricsPartType type) {
        this.components = components;
        this.type = type;
    }

    /**
     * Returns components of the <Code>SongLyricsComposite</Code> instance.
     * @return Components of the <Code>SongLyricsComposite</Code> instance.
     */
    @Override
    public List<SongLyrics> getComponents() {
        return components;
    }

    /**
     * Sets components of the <Code>SongLyricsComposite</Code> instance.
     * @param components New list of components.
     */
    public void setComponents(List<SongLyrics> components) {
        this.components = components;
    }

    /**
     * Returns type of the <Code>SongLyricsComposite</Code> instance.
     * @return Type of the <Code>SongLyricsComposite</Code> instance.
     */
    @Override
    public SongLyricsPartType getType() {
        return type;
    }

    public void setType(SongLyricsPartType type) {
        this.type = type;
    }

    /**
     * Adds new component to the list of components.
     * @param component New component to be added.
     */
    @Override
    public void add(SongLyrics component) {
        components.add(component);
    }

    /**
     * Creates string representation of <Code>SongLyricsComposite</Code> instance.
     * @return String representation of <Code>SongLyricsComposite</Code> instance.
     */
    @Override
    public String toString() {
       StringBuilder stringBuilder = new StringBuilder();

       for (SongLyrics component : components) {
           stringBuilder.append(component.toString());
       }

       return stringBuilder.toString();
    }

    /**
     * Checks the equality of this <Code>SongLyricsComposite</Code> instance and other object.
     * @param object Object which is to be compared with this <Code>SongLyricsComposite</Code> instance.
     * @return <Code>True</Code> if this <Code>SongLyricsComposite</Code> instance equals to object,
     * <Code>False</Code> if not.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SongLyricsComposite that = (SongLyricsComposite) object;
        return Objects.equals(components, that.components) &&
                type == that.type;
    }

    /**
     * Calculates hashcode of <Code>SongLyricsComposite</Code> instance.
     * @return Hashcode of <Code>SongLyricsComposite</Code> instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(components, type);
    }
}
