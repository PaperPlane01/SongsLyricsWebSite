package kz.javalab.songslyricswebsite.command;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.service.ArtistsService;

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

        ArtistsService artistsService = new ArtistsService();

        String json = (new Gson()).toJson(artistsService.getArtistsByLetter(letter));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
