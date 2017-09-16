package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.InvalidRatingValueException;
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

        if (request.getSession().getAttribute("user") == null) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.songratingreceivedfromnonloggedinuser"));
            sendJsonResponse(responseMap, response);
            return;
        }

        User currentUser = (User) request.getSession().getAttribute("user");
        int songID = Integer.valueOf(request.getParameter("songID"));
        int rating = Integer.valueOf(request.getParameter("rating"));

        SongsRatingsManager songsRatingsManager = new SongsRatingsManager();

        try {
            if (songsRatingsManager.checkIfUserRatedSong(currentUser.getID(), songID)) {
                songsRatingsManager.alterSongRating(currentUser.getID(), songID, rating);
            } else {
                songsRatingsManager.rateSong(currentUser.getID(), songID, rating);
            }

            responseMap.put("status", "SUCCESS");
            sendJsonResponse(responseMap, response);
        } catch (InvalidRatingValueException e) {
            responseMap.put("status", "FAILURE");
            responseMap.put("message", resourceBundle.getString("labels.errors.invalidratingvalue"));
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
