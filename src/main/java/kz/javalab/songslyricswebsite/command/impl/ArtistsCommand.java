package kz.javalab.songslyricswebsite.command.impl;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.service.ArtistsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PaperPlane on 23.08.2017.
 */
public class ArtistsCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Character letter = request.getParameter("letter").charAt(0);

        ArtistsManager artistsManager = new ArtistsManager();

        String json = new Gson().toJson(artistsManager.getArtistsByLetter(letter));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
