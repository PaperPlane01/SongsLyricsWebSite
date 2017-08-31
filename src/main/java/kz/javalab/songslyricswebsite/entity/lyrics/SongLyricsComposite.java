package kz.javalab.songslyricswebsite.entity.lyrics;

import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SongLyricsComposite implements SongLyrics {

    private List<SongLyrics> components = new ArrayList<>();
    private SongLyricsPartType type;

    public SongLyricsComposite() {
    }

    public SongLyricsComposite(List<SongLyrics> components, SongLyricsPartType type) {
        this.components = components;
        this.type = type;
    }

    @Override
    public List<SongLyrics> getComponents() {
        return components;
    }

    public void setComponents(List<SongLyrics> components) {
        this.components = components;
    }

    @Override
    public SongLyricsPartType getType() {
        return type;
    }

    public void setType(SongLyricsPartType type) {
        this.type = type;
    }

    @Override
    public void add(SongLyrics component) {
        components.add(component);
    }

    @Override
    public String toString() {
       StringBuilder stringBuilder = new StringBuilder();

       components.forEach(component -> stringBuilder.append(component.toString()));

       return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongLyricsComposite that = (SongLyricsComposite) o;
        return Objects.equals(components, that.components) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(components, type);
    }
}
