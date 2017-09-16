package kz.javalab.songslyricswebsite.command.impl.jsoncommand;

import kz.javalab.songslyricswebsite.command.JSONCommand;
import kz.javalab.songslyricswebsite.service.ArtistsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by PaperPlane on 23.08.2017.
 */
public class ArtistsLettersCommand extends JSONCommand {

    public ArtistsLettersCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArtistsManager artistsManager = new ArtistsManager();

        List<Character> artistsLetters = artistsManager.getArtistsLetters();

        sendJsonResponse(artistsLetters, response);
    }

    @Override
    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseData, response);
    }
}
