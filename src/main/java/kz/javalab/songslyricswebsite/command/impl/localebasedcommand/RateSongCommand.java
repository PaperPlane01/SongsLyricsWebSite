package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.InvalidRatingValueException;
import kz.javalab.songslyricswebsite.exception.NoSuchSongException;
import kz.javalab.songslyricswebsite.exception.SongRatingException;
import kz.javalab.songslyricswebsite.exception.UserNotLoggedInException;
import kz.javalab.songslyricswebsite.service.SongsRatingsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by PaperPlane on 07.09.2017.
 */
public class RateSongCommand extends LocaleBasedCommand {

    public RateSongCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", getLocaleFromRequest(request));
        Map<String, String> responseMap = new LinkedHashMap<>();
        RequestWrapper requestWrapper = new RequestWrapper(request);

        SongsRatingsManager songsRatingsManager = new SongsRatingsManager(requestWrapper);

        try {
            songsRatingsManager.rateSong();
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.SUCCESS);
            sendJsonResponse(responseMap, response);
        } catch (InvalidRatingValueException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.INVALID_RATING_VALUE));
            sendJsonResponse(responseMap, response);
        } catch (UserNotLoggedInException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.SONG_RATING_RECEIVED_FROM_NOT_LOGGED_IN_USER));
            sendJsonResponse(responseMap, response);
        } catch (NoSuchSongException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.NO_SUCH_SONG));
            sendJsonResponse(responseMap, response);;
        } catch (SongRatingException e) {
            e.printStackTrace();
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
