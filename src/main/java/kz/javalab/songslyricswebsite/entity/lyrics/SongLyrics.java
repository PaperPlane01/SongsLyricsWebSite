package kz.javalab.songslyricswebsite.entity.lyrics;

import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;

import java.util.List;

public interface SongLyrics {

    void add(SongLyrics component);

    SongLyricsPartType getType();

    List<SongLyrics> getComponents();
}
