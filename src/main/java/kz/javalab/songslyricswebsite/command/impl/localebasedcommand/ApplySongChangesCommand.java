package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.exception.*;
import kz.javalab.songslyricswebsite.service.SongsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class is responsible for modifying existing song.
 */
public class ApplySongChangesCommand extends LocaleBasedCommand {

    public ApplySongChangesCommand() {

    }

    /**
     * Modifies existing song and informs the user whether attempt of modifying existing song has been successful.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> responseMap = new LinkedHashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));

        RequestWrapper requestWrapper = new RequestWrapper(request);
        SongsManager songsManager = new SongsManager(requestWrapper);

        try {
            songsManager.updateSong();
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.SUCCESS);
            responseMap.put(ResponseConstants.Messages.MESSAGE, "Everything is ok!");
            sendJsonResponse(responseMap, response);
        } catch (LyricsParsingException e) {
            e.printStackTrace();
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_SONG_LYRICS));
            sendJsonResponse(responseMap, response);
        } catch (InvalidSongNameException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_SONG_NAME));
            sendJsonResponse(responseMap, response);
        } catch (InvalidFeaturedArtistsException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_FEATURED_ARTISTS));
            sendJsonResponse(responseMap, response);
        } catch (InvalidYouTubeVideoIDException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_YOUTUBE_VIDEO_ID));
            sendJsonResponse(responseMap, response);
        } catch (InvalidSongGenresException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_SONG_GENRES));
            sendJsonResponse(responseMap, response);
        } catch (InvalidArtistNameException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_ARTIST_NAME));
            sendJsonResponse(responseMap, response);
        } catch (TooLongOrEmptyLyricsException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.TOO_LONG_LYRICS));
            sendJsonResponse(responseMap, response);
        } catch (InvalidFeaturedArtistNameException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.FEATURED_ARTIST_NAME_IS_TOO_LONG));
            sendJsonResponse(responseMap, response);
        } catch (NoSuchSongException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.NO_SUCH_SONG));
            sendJsonResponse(responseMap, response);
        } catch (SongAlteringException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.ERROR_WHILE_ALTERING_SONG));
            sendJsonResponse(responseMap, response);
        }
    }

    @Override
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return super.getLocaleFromRequest(request);
    }

    @Override
    protected void sendJsonResponse(Object responseObject, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseObject, response);
    }
}
