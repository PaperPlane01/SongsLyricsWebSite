package kz.javalab.songslyricswebsite.entity.artist;

import java.util.Objects;

/**
 * This class represents artist.
 */
public class Artist {

    /**
     * ID of the artist.
     */
    private int id;

    /**
     * Name of the artist.
     */
    private String name;

    /**
     * Constructs new <Code>Artist</Code> instance.
     */
    public Artist() {
        this.name = "";
    }

    /**
     * Constructs new <Code>Artist</Code> instance with pre-defined name.
     * @param name Name of the artist.
     */
    public Artist(String name) {
        this.name = name;
    }

    /**
     * Constructs new <Code>Artist</Code> instance with pre-defined name and ID.
     * @param id ID of the artist.
     * @param name Name of the artist.
     */
    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns ID of the artist.
     * @return ID of the artist.
     */
    public int getID() {
        return id;
    }

    /**
     * Sets new ID to the artist.
     * @param id New ID to be set.
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Returns name of the artist.
     * @return Name of the artist.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets new name to the artist.
     * @param name New name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks the equality of this <Code>Artist</Code> instance and other object.
     * @param object Object which is to be compared with this <Code>Artist</Code> instance.
     * @return <Code>True</Code> if this <Code>Artist</Code> instance equals to object,
     * <code>False</code> if not.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Artist artist = (Artist) object;
        return id == artist.id &&
                Objects.equals(name, artist.name);
    }

    /**
     * Calculates hashcode of this <Code>Artist</Code> instance.
     * @return Hashcode of this <Code>Artist</Code> instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
