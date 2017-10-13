package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import kz.javalab.songslyricswebsite.command.JSONCommand;
import kz.javalab.songslyricswebsite.command.LocaleBasedCommand;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.exception.DataAccessException;
import kz.javalab.songslyricswebsite.service.SongsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class is responsible for sending the list of 10 songs with the highest ratings to the user.
 */
public class TopTenRatedSongsCommand extends LocaleBasedCommand {

    public TopTenRatedSongsCommand() {
    }

    /**
     * Sends the list of 10 songs with highest ratings to the user.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SongsManager songsManager = new SongsManager();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(ResponseConstants.Messages.LABELS, getLocaleFromRequest(request));

        Map<String, Object> responseMap = new LinkedHashMap<>();

        try {
            List<Song> songs = songsManager.getTopTenRatedSongs();

            for (Song song : songs) {
                song.initTitle();
            }

            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.SUCCESS);
            responseMap.put(ResponseConstants.Messages.DATA, songs);
            sendJsonResponse(responseMap, response);
        } catch (DataAccessException e) {
            responseMap.put(ResponseConstants.Status.STATUS, ResponseConstants.Status.FAILURE);
            responseMap.put(ResponseConstants.Messages.MESSAGE, resourceBundle.getString(ResponseConstants.Messages.FAILED_TO_LOAD_DATA));
            sendJsonResponse(responseMap, response);
        }

    }

    @Override
    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseData, response);
    }
}
