package kz.javalab.songslyricswebsite.command;

import com.sun.deploy.net.HttpRequest;
import kz.javalab.songslyricswebsite.model.song.Song;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.model.song.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaperPlane on 07.08.2017.
 */
public class SongCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int songID = Integer.valueOf(request.getParameter("songID"));

        SongsService songsService = new SongsService();

        Song song = songsService.getSongByID(songID);
        SongLyrics songLyrics = song.getLyrics();
        String songTitle = song.getTitle();
        String youTubeLink = songsService.getYouTubeLinkBySongID(songID);
        Boolean isApproved = songsService.checkIfSongApproved(songID);

        List<SongLyrics> lyricsPartsAsList = new ArrayList<>();

        ((SongLyricsComposite) songLyrics).getComponents().forEach(part -> lyricsPartsAsList.add(part));

        request.setAttribute("songTitle", songTitle);
        request.setAttribute("listOfLyricsParts", lyricsPartsAsList);
        request.setAttribute("youTubeLink", youTubeLink);
        request.setAttribute("isApproved", isApproved);
        request.setAttribute("songID", songID);

        String page = ConfigurationManager.getProperty("path.page.song");

        request.getRequestDispatcher(page).forward(request, response);
    }
}
