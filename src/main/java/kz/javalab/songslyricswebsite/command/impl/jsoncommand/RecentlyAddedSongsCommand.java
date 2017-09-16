package kz.javalab.songslyricswebsite.command.impl.jsoncommand;

import kz.javalab.songslyricswebsite.command.JSONCommand;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.service.SongsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by PaperPlane on 16.09.2017.
 */
public class RecentlyAddedSongsCommand extends JSONCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SongsManager songsManager = new SongsManager();
        List<Song> recentlyAddedSongs = songsManager.getRecentlyAddedSongs();

        for (Song song : recentlyAddedSongs) {
            song.initTitle();
        }

        sendJsonResponse(recentlyAddedSongs, response);
    }

    @Override
    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseData, response);
    }
}
