package kz.javalab.songslyricswebsite.command.localebasedcommand;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.exception.*;
import kz.javalab.songslyricswebsite.service.SongsService;
import kz.javalab.songslyricswebsite.util.SongRetriever;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by PaperPlane on 30.08.2017.
 */

    public ApplySongChangesCommand() {

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SongRetriever songRetriever = new SongRetriever();
        Map<String, String> responseMap = new LinkedHashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));

        try {
            Song alteredSong = songRetriever.retrieveSongFromRequest(request);

            SongsService songsService = new SongsService();

            songsService.alterSong(alteredSong.getID(), alteredSong, songRetriever.getYouTubeLink());

            responseMap.put("status", "SUCCESS");
            responseMap.put("message", "Everything is ok!");
            sendJsonResponse(responseMap, response);

        }  catch (InvalidArtistNameException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.artistname.invalid"));
            sendJsonResponse(responseMap, response);
        } catch (InvalidSongNameException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.songname.invalid"));
            sendJsonResponse(responseMap, response);
        } catch (InvalidFeaturedArtistsException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.featuredartists.invalid"));
            sendJsonResponse(responseMap, response);
        } catch (InvalidSongGenresException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.songgenres.invalid"));
            sendJsonResponse(responseMap, response);
        } catch (TooLongOrEmptyLyricsException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.songlyrics.toolong"));
            sendJsonResponse(responseMap, response);
        } catch (InvalidYouTubeVideoIDException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.youtubevideoid.invalid"));
            sendJsonResponse(responseMap, response);
        } catch (InvalidFeaturedArtistNameException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.featuredartists.artistnametoolong"));
            sendJsonResponse(responseMap, response);
        } catch (LyricsParsingException e) {
            e.printStackTrace();
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.songlyrics.invalid"));
            sendJsonResponse(responseMap, response);
        } catch (NoSuchSongException e) {
            e.printStackTrace();
        } catch (SongAlteringException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", "cant alter song");
            sendJsonResponse(responseMap, response);
        }
    }

    private void sendJsonResponse(Map<String, String> responseMap, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(responseMap);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @Override
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return super.getLocaleFromRequest(request);
    }
}
