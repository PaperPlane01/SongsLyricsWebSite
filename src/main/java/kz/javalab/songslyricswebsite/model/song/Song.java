package kz.javalab.songslyricswebsite.model.song;


import kz.javalab.songslyricswebsite.model.song.artist.Artist;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyricsComposite;

import java.util.ArrayList;
import java.util.List;

public class Song {

    private int ID;
    private String name;
    private Artist artist = new Artist();
    private List<Artist> featuredArtists = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private SongLyrics lyrics = new SongLyricsComposite();

    public Song() {
    }

    public Song(int ID, String name, Artist artist, List<Artist> featuredArtists, List<String> genres, SongLyrics lyrics) {
        this.ID = ID;
        this.name = name;
        this.artist = artist;
        this.featuredArtists = featuredArtists;
        this.genres = genres;
        this.lyrics = lyrics;
    }

    public Song(String name, Artist artist, List<String> genres, SongLyrics lyrics) {
        this.name = name;
        this.artist = artist;
        this.genres = genres;
        this.lyrics = lyrics;
    }

    public Song(String name, Artist artist, List<Artist> featuredArtists, List<String> genres, SongLyrics lyrics) {
        this.name = name;
        this.artist = artist;
        this.featuredArtists = featuredArtists;
        this.genres = genres;
        this.lyrics = lyrics;
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Artist> getFeaturedArtists() {
        return featuredArtists;
    }

    public void setFeaturedArtists(List<Artist> featuredArtists) {
        this.featuredArtists = featuredArtists;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public SongLyrics getLyrics() {
        return lyrics;
    }

    public void setLyrics(SongLyrics lyrics) {
        this.lyrics = lyrics;
    }


    public String getTitle() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(artist.getName());

        if (hasFeaturedArtists()) {
            stringBuilder.append(" feat. ");

            for (int index = 0; index < featuredArtists.size(); index++) {
                if (index != featuredArtists.size() - 1) {
                    stringBuilder.append(" & ");
                }
                stringBuilder.append(featuredArtists.get(index).getName());
            }
        }

        stringBuilder.append(" — ");
        stringBuilder.append(name);
        return stringBuilder.toString();
    }

    public boolean hasFeaturedArtists() {
        return featuredArtists.size() != 0;
    }

}
