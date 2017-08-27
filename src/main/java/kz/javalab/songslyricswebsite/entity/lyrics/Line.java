package kz.javalab.songslyricswebsite.entity.lyrics;


import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;

import java.util.List;

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
}
