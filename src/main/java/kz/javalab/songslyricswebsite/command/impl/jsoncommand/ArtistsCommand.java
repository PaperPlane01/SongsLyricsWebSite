package kz.javalab.songslyricswebsite.command.impl.jsoncommand;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.command.JSONCommand;
import kz.javalab.songslyricswebsite.command.requestwrapper.RequestWrapper;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.service.ArtistsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by PaperPlane on 23.08.2017.
 */
public class ArtistsCommand extends JSONCommand {

    public ArtistsCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);

        ArtistsManager artistsManager = new ArtistsManager(requestWrapper);

        List<Artist> artists = artistsManager.getArtistsByLetter();

        sendJsonResponse(artists, response);
    }

    @Override
    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseData, response);
    }
}
