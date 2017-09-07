package kz.javalab.songslyricswebsite.util;

import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.exception.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by PaperPlane on 30.08.2017.
 */
public class SongRetriever {

    private String youTubeLink = new String();

    /**
     * Retrieves <Code>Song</Code> object from <Code>HttpServletRequest</Code>
     * @param request Request to be handled
     * @return <Code>Song</Code> object retrieved from <Code>HttpServletRequest</Code>
     * @throws InvalidArtistNameException Thrown if artist's name is too long or empty.
     * @throws InvalidSongNameException Thrown if song's name is too long or empty.
     * @throws InvalidFeaturedArtistsException Thrown if "songFeaturedArtists" parameter of request is too long.
     * @throws InvalidSongGenresException Thrown if "songGenres" parameter of request is too long.
     * @throws TooLongOrEmptyLyricsException Thrown if song lyrics are too long or empty.
     * @throws InvalidYouTubeVideoIDException Thrown if YouTubeVideoID is too long or empty.
     * @throws InvalidFeaturedArtistNameException Thrown if one of featured artist's name is too long.
     * @throws LyricsParsingException Thrown if song lyrics are invalid.
     */
    public Song retrieveSongFromRequest(HttpServletRequest request) throws InvalidArtistNameException, InvalidSongNameException, InvalidFeaturedArtistsException, InvalidSongGenresException, TooLongOrEmptyLyricsException, InvalidYouTubeVideoIDException, InvalidFeaturedArtistNameException, LyricsParsingException, NoSuchSongException {

        Song song = new Song();

        try {
            int songID = Integer.valueOf(request.getParameter("songID"));
            String songName = request.getParameter("songName");
            String artistName = request.getParameter("songArtist");
            String songFeaturedArtists = request.getParameter("songFeaturedArtists");
            String songGenres = request.getParameter("songGenres");
            String songLyricsAsString = request.getParameter("songLyrics");
            String youTubeLink = request.getParameter("youTubeLink");

            if (!validateArtistName(artistName)) {
                throw new InvalidArtistNameException();
            }

            if (!validateSongName(songName)) {
                throw new InvalidSongNameException();
            }

            if (!validateFeaturedArtists(songFeaturedArtists)) {
                throw new InvalidFeaturedArtistsException();
            }

            if (!validateSongGenres(songGenres)) {
                throw new InvalidSongGenresException();
            }

            if (!validateLyrics(songLyricsAsString)) {
                throw new TooLongOrEmptyLyricsException();
            }

            if (!validateYouTubeVideoID(youTubeLink)) {
                throw new InvalidYouTubeVideoIDException();
            }

            this.youTubeLink = youTubeLink;

            song.setName(songName);
            song.setID(songID);
            song.setArtist(new Artist(artistName));

            if (!songFeaturedArtists.trim().isEmpty()) {
                StringTokenizer stringTokenizer = new StringTokenizer(songFeaturedArtists, ";");

                List<Artist> featuredArtists = new ArrayList<>();

                while (stringTokenizer.hasMoreTokens()) {
                    String featuredArtistName = stringTokenizer.nextToken().trim();

                    if (!validateArtistName(featuredArtistName)) {
                        throw new InvalidFeaturedArtistNameException();
                    }

                    featuredArtists.add(new Artist(featuredArtistName));
                }

                song.setFeaturedArtists(featuredArtists);
            }

            if (!songGenres.isEmpty()) {
                List<String> songGenresList = new ArrayList<>();

                StringTokenizer stringTokenizer = new StringTokenizer(songGenres, ";");

                while (stringTokenizer.hasMoreTokens()) {
                    songGenresList.add(stringTokenizer.nextToken().trim());
                }

                song.setGenres(songGenresList);
            }

            LyricsParser lyricsParser = new LyricsParser();

            SongLyrics songLyrics = lyricsParser.buildLyricsFromString(songLyricsAsString);

            song.setLyrics(songLyrics);

            return song;
        } catch (NumberFormatException ex) {
            throw new NoSuchSongException();
        }
    }

    public String getYouTubeLink() {
        return youTubeLink;
    }

    private boolean validateSongName(String songName) {
        int minSize = 0;
        int maxSize = 100;

        boolean result = true;

        if (songName.trim().length() == minSize) {
            result = false;
        }

        if (songName.trim().length() > maxSize) {
            result = false;
        }

        return result;
    }

    private boolean validateArtistName(String artistName) {
        int minSize = 0;
        int maxSize = 50;
        boolean result = true;

        if (artistName.trim().length() > maxSize) {
            result = false;
        }

        if (artistName.length() == minSize) {
            result = false;
        }

        return result;
    }

    private boolean validateFeaturedArtists(String featuredArtists) {
        int maxSize = 250;
        boolean result = true;

        if (featuredArtists.length() > maxSize) {
            result = false;
        }


        return result;
    }

    private boolean validateSongGenres(String songGenres) {
        int maxSize = 130;
        boolean result = false;

        if (songGenres.length() < maxSize) {
            result = true;
        }

        return result;
    }

    private boolean validateLyrics(String lyrics) {
        int minSize = 0;
        int maxSize = 5000;

        if (lyrics.length() == minSize) {
            return false;
        }

        if (lyrics.length() > maxSize) {
            return false;
        }

        return true;
    }

    private boolean validateYouTubeVideoID(String youTubeVideoID) {
        int maxSize = 15;
        boolean result = false;

        if (youTubeVideoID == null) {
            //YouTube link is not necessary.
            result = true;
            return result;
        }

        if (youTubeVideoID.length() < maxSize) {
            result = true;
        }

        return  result;
    }
}
