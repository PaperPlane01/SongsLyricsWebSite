package kz.javalab.songslyricswebsite.command;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.service.ArtistsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by PaperPlane on 23.08.2017.
 */
public class ArtistsLettersCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArtistsService artistsService = new ArtistsService();

        List<Character> artistsLetters = artistsService.getArtistsLetters();

        System.out.println(artistsLetters);

        String json = (new Gson()).toJson(artistsLetters);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
