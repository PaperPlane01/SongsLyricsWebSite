package kz.javalab.songslyricswebsite.model.song.lyrics;


import java.util.ArrayList;
import java.util.List;

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
}
