package kz.javalab.songslyricswebsite.model.fortesting;

import kz.javalab.songslyricswebsite.model.song.Song;
import kz.javalab.songslyricswebsite.model.song.artist.Artist;

import java.util.Arrays;

/**
 * Created by PaperPlane on 15.07.2017.
 */
public class SongExample {

    private static Song instance;

    private SongExample() {

    }

    public static Song getInstance() {
        if (instance == null) {
            initInstance();
        }

        return instance;
    }

    private static void initInstance() {
        instance = new Song();
        instance.setID(1);
        instance.setArtist(new Artist(1, "The Chainsmokers"));
        instance.setName("Don't let me down");
        instance.setLyrics(SongLyricsExample.getInstance());

        String[] genres = {"Trap", "EDM"};

        instance.setGenres(Arrays.asList(genres));

    }
}
