package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.model.song.Song;
import kz.javalab.songslyricswebsite.model.song.lyrics.Line;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyricsPartType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by PaperPlane on 02.08.2017.
 */
public class SongReader {

    public SongLyricsComposite buildLyricsFromString(String lyricsAsText) {
        SongLyricsComposite songLyrics = new SongLyricsComposite();
        List<String> lines = Arrays.asList(lyricsAsText.split("\n"));

        SongLyricsComposite lyricsPart = new SongLyricsComposite();

        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            String line = lines.get(lineIndex);

            switch (line.trim()) {
                case "[INTRO]":
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.INTRO);
                    System.out.println("Intro found");
                    break;
                case "[VERSE]":
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.VERSE);
                    System.out.println("Verse found");
                    break;
                case "[CHORUS]":
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.CHORUS);
                    System.out.println("Chorus found");
                    break;
                case "[HOOK]":
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.HOOK);
                    break;
                case "[BRIDGE]":
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.BRIDGE);
                    break;
                case "[OUTRO]":
                    lyricsPart = new SongLyricsComposite();
                    lyricsPart.setType(SongLyricsPartType.OUTRO);
                    break;
                case "[/]":
                    songLyrics.add(lyricsPart);
                    lyricsPart = null;
                default:
                    if (lyricsPart != null) {
                        System.out.println(line);
                        lyricsPart.add(new Line(line));
                    }
                    break;
            }
        }

        return songLyrics;
    }
}