package kz.javalab.songslyricswebsite.entity.song;

import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.entity.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a song.
 */
public class Song {

    /**
     * ID of the song.
     */
    private int id;

    /**
     * Name of the song.
     */
    private String name;

    /**
     * Artist of the song.
     */
    private Artist artist = new Artist();

    /**
     * Featured artists of the song.
     */
    private List<Artist> featuredArtists = new ArrayList<>();

    /**
     * Genres of the song.
     */
    private List<String> genres = new ArrayList<>();

    /**
     * Lyrics of the song.
     */
    private SongLyrics lyrics = new SongLyricsComposite();

    /**
     * Indicates if song has been approved.
     */
    private boolean approved;

    /**
     * Title of the song.
     */
    private String title;

    /**
     * Average rating of the song.
     */
    private double averageRating;

    /**
     * ID of YouTube's video of the song.
     */
    private String youTubeVideoID;

    private User contributedUser;

    /**
     * Constructs <Code>Song</Code> instance.
     */
    public Song() {
    }

    /**
     * Returns ID of the song.
     * @return ID of the song.
     */
    public int getID() {
        return id;
    }

    /**
     * Modifies ID of the song.
     * @param ID New ID to be set.
     */
    public void setID(int ID) {
        this.id = ID;
    }

    /**
     * Returns name of the song.
     * @return Name of the song.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifies name of the song.
     * @param name New name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns artist of the song.
     * @return Artist of the song.
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Modifies artist of the song.
     * @param artist New artist to be set.
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Returns featured artists of the song.
     * @return Featured artists of the song.
     */
    public List<Artist> getFeaturedArtists() {
        return featuredArtists;
    }

    /**
     * Modifies featured artists of the song.
     * @param featuredArtists New list of featured artists of the song to be set.
     */
    public void setFeaturedArtists(List<Artist> featuredArtists) {
        this.featuredArtists = featuredArtists;
    }

    /**
     * Returns genres of the song.
     * @return Genres of the song.
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Modifies genres of the song.
     * @param genres New list of genres of the song to be set.
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * Returns lyrics of the song.
     * @return Lyrics of the song.
     */
    public SongLyrics getLyrics() {
        return lyrics;
    }

    /**
     * Modifies lyrics of the song.
     * @param lyrics New lyrics of the song to be set.
     */
    public void setLyrics(SongLyrics lyrics) {
        this.lyrics = lyrics;
    }

    /**
     * Allows to know if song has been approved.
     * @return <Code>True</Code> if song has been approved, <Code>False</Code> if not.
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Modifies <Code>approved</Code> value.
     * @param approved <Code>approved</Code> value to be set.
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * Returns YouTube video ID.
     * @return YouTube video ID.
     */
    public String getYouTubeVideoID() {
        return youTubeVideoID;
    }

    /**
     * Modifies YouTube video ID.
     * @param youTubeVideoID New YouTube video ID to be set.
     */
    public void setYouTubeVideoID(String youTubeVideoID) {
        this.youTubeVideoID = youTubeVideoID;
    }

    public User getContributedUser() {
        return contributedUser;
    }

    public void setContributedUser(User contributedUser) {
        this.contributedUser = contributedUser;
    }

    /**
     * Returns title of the song.
     * @return Title of the song.
     */
    public String getTitle() {
        if (title == null || title.isEmpty()) {
            initTitle();
        }

        return title;
    }

    /**
     * Initializes title of the song.
     */
    public void initTitle() {
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

        title = stringBuilder.toString();
    }

    /**
     * Returns average rating of the song.
     * @return Average rating of the song.
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * Modifies average rating of the song.
     * @param averageRating Mew average rating to be set.
     */
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Indicates if song has featured artists.
     * @return <Code>True</Code> if song has featured artists, <Code>False</Code> if not.
     */
    public boolean hasFeaturedArtists() {
        return featuredArtists.size() != 0;
    }

}
