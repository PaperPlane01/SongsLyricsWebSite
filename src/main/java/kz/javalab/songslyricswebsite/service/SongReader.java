package kz.javalab.songslyricswebsite.service;

import kz.javalab.songslyricswebsite.entity.lyrics.Line;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsPartType;
import kz.javalab.songslyricswebsite.exception.LyricsParsingException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by PaperPlane on 02.08.2017.
 */
public class SongReader {

    public SongLyricsComposite buildLyricsFromString(String lyricsAsText) throws LyricsParsingException {
        SongLyricsComposite songLyrics = new SongLyricsComposite();
        List<String> lines = Arrays.asList(lyricsAsText.split("\n"));

        SongLyricsComposite lyricsPart = new SongLyricsComposite();

        try {
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
                        break;
                    case "":
                        break;
                    default:
                        if (lyricsPart != null) {
                            System.out.println(line);
                            lyricsPart.add(new Line(line));
                        }
                        break;
                }
            }

            return songLyrics;
        } catch (Exception e) {
            throw new LyricsParsingException();
        }
    }
}