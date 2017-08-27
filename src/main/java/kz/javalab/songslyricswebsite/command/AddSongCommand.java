package kz.javalab.songslyricswebsite.command;

import kz.javalab.songslyricswebsite.exception.SongAddingException;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongReader;
import kz.javalab.songslyricswebsite.service.SongsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by PaperPlane on 19.08.2017.
 */
public class AddSongCommand implements ActionCommand{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SongsService songsService = new SongsService();
        SongReader songReader = new SongReader();
        Song song = new Song();

        String songName = request.getParameter("songName");
        song.setName(songName);

        String artistName = request.getParameter("songArtist");
        song.setArtist(new Artist(artistName));

        String songFeaturedArtists = request.getParameter("songFeaturedArtists");
        StringTokenizer stringTokenizer = new StringTokenizer(songFeaturedArtists, ";");
        List<Artist> featuredArtists = new ArrayList<>();

        while (stringTokenizer.hasMoreTokens()) {
            featuredArtists.add(new Artist(stringTokenizer.nextToken()));
        }

        song.setFeaturedArtists(featuredArtists);

        String songGenres = request.getParameter("songGenres");
        stringTokenizer = new StringTokenizer(songGenres, ";");
        List<String> genres = new ArrayList<>();

        while (stringTokenizer.hasMoreTokens()) {
            genres.add(stringTokenizer.nextToken());
        }

        song.setGenres(genres);

        String songLyricsAsString = request.getParameter("songLyrics");
        SongLyrics songLyrics = songReader.buildLyricsFromString(songLyricsAsString);
        song.setLyrics(songLyrics);

        String youTubeLink = request.getParameter("youTubeLink");

        String page = new String();

        try {
            songsService.addSongToDatabase(song, youTubeLink);
            page = ConfigurationManager.getProperty("path.page.songhasbeenadded");
            request.getRequestDispatcher(page).forward(request, response);
        } catch (SongAddingException e) {
            e.printStackTrace();
        }

        page = ConfigurationManager.getProperty("path.page.songhasbeenadded");



    }
}
