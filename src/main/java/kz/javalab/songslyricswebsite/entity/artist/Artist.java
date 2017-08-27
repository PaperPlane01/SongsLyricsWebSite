package kz.javalab.songslyricswebsite.entity.artist;

public class Artist {

    private int ID;
    private String name;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public Artist(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (ID != artist.ID) return false;
        return name.equals(artist.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (ID ^ (ID >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
}
