package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.exception.DataAccessException;
import kz.javalab.songslyricswebsite.service.ArtistsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * This class is responsible for sending artists letters to the user.
 */
public class ArtistsLettersCommand extends LocaleBasedCommand {

    public ArtistsLettersCommand() {
    }

    /**
     * Sends artists letters to the user.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArtistsManager artistsManager = new ArtistsManager();
        Map<String, Object> responseMap = new LinkedHashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(ResponseConstants.Messages.LABELS, getLocaleFromRequest(request));

        try {
            List<Character> artistsLetters = artistsManager.getArtistsLetters();
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.SUCCESS);
            responseMap.put(ResponseConstants.Messages.DATA, artistsLetters);
            sendJsonResponse(responseMap, response);
        } catch (DataAccessException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.FAILED_TO_LOAD_DATA));
            sendJsonResponse(responseMap, response);
        }

    }

}
