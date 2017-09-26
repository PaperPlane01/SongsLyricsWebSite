package kz.javalab.songslyricswebsite.command.impl.jsoncommand;

import kz.javalab.songslyricswebsite.command.JSONCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.service.SongsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for sending list of songs performed by the specific artist to the user.
 */
public class ListOfSongsCommand extends JSONCommand {

    public ListOfSongsCommand() {
    }

    /**
     * Sends list of songs performed by the specific artist to the user.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);

        SongsManager songsManager = new SongsManager(requestWrapper);

        List<Song> songsPerformedByArtist = songsManager.getSongsByArtist();

        sendJsonResponse(songsPerformedByArtist, response);
    }

    @Override
    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseData, response);
    }
}
