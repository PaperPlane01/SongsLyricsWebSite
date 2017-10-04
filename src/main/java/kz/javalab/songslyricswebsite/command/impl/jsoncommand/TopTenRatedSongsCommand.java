package kz.javalab.songslyricswebsite.command.impl.jsoncommand;

import kz.javalab.songslyricswebsite.command.JSONCommand;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.service.SongsManager;
import kz.javalab.songslyricswebsite.service.SongsRatingsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for sending the list of 10 songs with the highest ratings to the user.
 */
public class TopTenRatedSongsCommand extends JSONCommand {

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

        List<Song> songs = songsManager.getTopTenRatedSongs();

        for (Song song : songs) {
            song.initTitle();
        }

        sendJsonResponse(songs, response);
    }

    @Override
    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseData, response);
    }
}
