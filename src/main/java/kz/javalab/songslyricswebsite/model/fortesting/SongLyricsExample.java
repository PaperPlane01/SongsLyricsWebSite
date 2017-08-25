package kz.javalab.songslyricswebsite.model.fortesting;

import kz.javalab.songslyricswebsite.model.song.lyrics.Line;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyricsPartType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaperPlane on 15.07.2017.
 */
public class SongLyricsExample {
    private static SongLyricsComposite instance;

    private SongLyricsExample() {

    }

    public static SongLyricsComposite getInstance() {
        if (instance == null) {
            initInstance();
        }

        return instance;
    }

    private static void initInstance() {
        instance = new SongLyricsComposite();

        SongLyricsComposite firstVerse = new SongLyricsComposite();
        firstVerse.setType(SongLyricsPartType.VERSE);
        initFirstVerse(firstVerse);

        SongLyricsComposite chorus = new SongLyricsComposite();
        chorus.setType(SongLyricsPartType.CHORUS);
        initChorus(chorus);

        SongLyricsComposite secondVerse = new SongLyricsComposite();
        secondVerse.setType(SongLyricsPartType.VERSE);

        initSecondVerse(secondVerse);

        instance.add(firstVerse);
        instance.add(chorus);
        instance.add(secondVerse);
        instance.add(chorus);
    }

    private static void initFirstVerse(SongLyricsComposite firstVerse) {
        List<String> lines = new ArrayList<>();

        lines.add("Crashing, hit a wall");
        lines.add("Right now I need a miracle");
        lines.add("Hurry up now, I need a miracle");
        lines.add("Stranded, reaching out");
        lines.add("I call your name, but you're not around");
        lines.add("I say your name, but you're not around");

        lines.forEach(line -> firstVerse.add(new Line(line)));
    }

    private static void initChorus(SongLyricsComposite chorus) {
        List<String> lines = new ArrayList<>();

        lines.add("I need you, I need you, I need you right now");
        lines.add("Yeah, I need you right now");
        lines.add("So don't let me, don't let me, don't let me down");
        lines.add("I think I'm losing my mind now");
        lines.add("It's in my head, darling I hope");
        lines.add("That you'll be here when I need you the most");
        lines.add("So don't let me, don't let me, don't let me down");
        lines.add("Don't let me down!");

        lines.forEach(line -> chorus.add(new Line(line)));
    }

    private static void initSecondVerse(SongLyricsComposite secondVerse) {
        List<String> lines = new ArrayList<>();

        lines.add("We're running out of time");
        lines.add("I really thought you were on my side");
        lines.add("But now there's nobody on my side");

        lines.forEach(line -> secondVerse.add(new Line(line)));
    }

}
