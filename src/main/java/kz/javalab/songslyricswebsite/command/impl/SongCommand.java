package kz.javalab.songslyricswebsite.command.impl;


import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.NoSuchSongException;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongsManager;
import kz.javalab.songslyricswebsite.service.SongsRatingsManager;

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

        try {
            int songID = Integer.valueOf(request.getParameter("songID"));

            SongsManager songsManager = new SongsManager();

            Song song = null;
            try {
                song = songsManager.getSongByID(songID);
                SongLyrics songLyrics = song.getLyrics();
                String songTitle = song.getTitle();
                String youTubeLink = songsManager.getYouTubeLinkBySongID(songID);
                Boolean isApproved = songsManager.checkIfSongApproved(songID);

                List<SongLyrics> lyricsPartsAsList = new ArrayList<>();

                for (SongLyrics songPart : songLyrics.getComponents()) {
                    lyricsPartsAsList.add(songPart);
                }

                request.setAttribute("songTitle", songTitle);
                request.setAttribute("listOfLyricsParts", lyricsPartsAsList);
                request.setAttribute("youTubeLink", youTubeLink);
                request.setAttribute("isApproved", isApproved);
                request.setAttribute("songID", songID);

                if (request.getSession().getAttribute("user") != null) {
                    User currentUser = (User) request.getSession().getAttribute("user");

                    SongsRatingsManager songsRatingsManager = new SongsRatingsManager();

                    Boolean userHasRatedSong = songsRatingsManager.checkIfUserRatedSong(currentUser.getID(), song.getID());

                    request.setAttribute("userHasRatedSong", userHasRatedSong);

                    if (userHasRatedSong) {
                        int userRatingOfSong = songsRatingsManager.getUserRatingOfSong(currentUser.getID(), song.getID());
                        request.setAttribute("userRating", userRatingOfSong);
                    }
                }

                String page = ConfigurationManager.getProperty("path.page.song");
                request.getRequestDispatcher(page).forward(request, response);
            } catch (NoSuchSongException e) {
                String page = ConfigurationManager.getProperty("path.page.nosuchsong");
                request.getRequestDispatcher(page).forward(request, response);
            }

        } catch (NumberFormatException ex) {
            String page = ConfigurationManager.getProperty("path.page.nosuchsong");
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
