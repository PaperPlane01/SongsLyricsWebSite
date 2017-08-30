package kz.javalab.songslyricswebsite.command.localebasedcommand;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.exception.LyricsParsingException;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.exception.SongAddingException;
import kz.javalab.songslyricswebsite.exception.SuchSongAlreadyExistsException;
import kz.javalab.songslyricswebsite.util.LyricsParser;
import kz.javalab.songslyricswebsite.service.SongsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by PaperPlane on 19.08.2017.
 */
public class AddSongCommand extends LocaleBasedCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SongsService songsService = new SongsService();
        Song song = new Song();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));

        Map<String, String> responseMap = new LinkedHashMap<>();

        String songName = request.getParameter("songName");
        String artistName = request.getParameter("songArtist");
        String songFeaturedArtists = request.getParameter("songFeaturedArtists");
        String songGenres = request.getParameter("songGenres");
        String songLyricsAsString = request.getParameter("songLyrics");
        String youTubeLink = request.getParameter("youTubeLink");

        if (!validateSongName(songName)) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.songname.invalid"));
            sendJsonResponse(responseMap, response);
            return;
        }

        if (!validateArtistName(artistName)) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.artistname.invalid "));
            sendJsonResponse(responseMap, response);
            return;
        }

        if (!validateFeaturedArtists(songFeaturedArtists)) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.featuredartists.invalid"));
            sendJsonResponse(responseMap, response);
            return;
        }

        if (!validateSongGenres(songGenres)) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.songgenres.invalid"));
            sendJsonResponse(responseMap, response);
            return;
        }

        if (!validateYouTubeVideoID(youTubeLink)) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.youtubevideoid.invalid"));
            sendJsonResponse(responseMap, response);
            return;
        }

        song.setName(songName);
        song.setArtist(new Artist(artistName));

        if (!songFeaturedArtists.trim().isEmpty()) {
            StringTokenizer stringTokenizer = new StringTokenizer(songFeaturedArtists, ";");

            List<Artist> featuredArtists = new ArrayList<>();

            while (stringTokenizer.hasMoreTokens()) {
                String featuredArtistName = stringTokenizer.nextToken().trim();

                if (!validateArtistName(featuredArtistName)) {
                    responseMap.put("status", "FAILURE");
                    responseMap.put("message", resourceBundle.getString("labels.errors.featuredartists.artistnametoolong"));
                    sendJsonResponse(responseMap, response);
                    return;
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


        try {
            SongLyrics songLyrics = lyricsParser.buildLyricsFromString(songLyricsAsString);
            song.setLyrics(songLyrics);
            songsService.addSongToDatabase(song, youTubeLink);

            responseMap.put("status", "SUCCESS");
            responseMap.put("message", resourceBundle.getString("labels.songhasbeenadded"));
            sendJsonResponse(responseMap, response);
        } catch (LyricsParsingException e) {
            e.printStackTrace();
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.songlyrics.invalid"));
            sendJsonResponse(responseMap, response);
        } catch (SuchSongAlreadyExistsException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.suchsongalreadyexists"));
            sendJsonResponse(responseMap, response);
        } catch (SongAddingException e) {
            e.printStackTrace();
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.errorwhileadding"));
            sendJsonResponse(responseMap, response);
        }

    }

    private void sendJsonResponse(Map<String, String> responseMap, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(responseMap);

        response.setContentType("application/json");
        response.getWriter().write(json);
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

    private boolean validateYouTubeVideoID(String youTubeVideoID) {
        int maxSize = 15;
        boolean result = false;

        if (youTubeVideoID == null) {
            result = true;
            return result;
        }

        if (youTubeVideoID.length() < maxSize) {
            result = true;
        }

        return  result;
    }

    @Override
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return super.getLocaleFromRequest(request);
    }
}
