package kz.javalab.songslyricswebsite.entity.lyrics;

import java.util.List;
import java.util.Objects;

public class Line implements SongLyrics {

    private String content;
    private static final SongLyricsPartType TYPE = SongLyricsPartType.LINE;

    public Line() {
    }

    public Line(String content) {
        this.content = content;
    }

    @Override
    public void add(SongLyrics component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SongLyricsPartType getType() {
        return TYPE;
    }

    @Override
    public List<SongLyrics> getComponents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(content, line.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
