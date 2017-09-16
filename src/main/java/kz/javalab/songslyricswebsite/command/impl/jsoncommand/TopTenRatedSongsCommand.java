package kz.javalab.songslyricswebsite.command.impl.jsoncommand;

import kz.javalab.songslyricswebsite.command.JSONCommand;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.service.SongsRatingsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PaperPlane on 16.09.2017.
 */
public class TopTenRatedSongsCommand extends JSONCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SongsRatingsManager songsRatingsManager = new SongsRatingsManager();

        List<Song> songs = songsRatingsManager.getTopTenRatedSongs();

        for (Song song : songs) {
            song.initTitle();
            System.out.println(song.getTitle());
        }

        sendJsonResponse(songs, response);
    }

    @Override
    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseData, response);
    }
}
