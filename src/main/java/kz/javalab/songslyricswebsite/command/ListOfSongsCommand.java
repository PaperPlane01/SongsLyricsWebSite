package kz.javalab.songslyricswebsite.command;

import com.google.gson.Gson;
import com.sun.deploy.net.HttpRequest;
import kz.javalab.songslyricswebsite.model.song.artist.Artist;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by PaperPlane on 07.08.2017.
 */
public class ListOfSongsCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SongsService songsService = new SongsService();

        String artistName = request.getParameter("artistName");

        Artist artist = new Artist(artistName);

        String json = (new Gson()).toJson(songsService.getSongsByArtist(artist));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
