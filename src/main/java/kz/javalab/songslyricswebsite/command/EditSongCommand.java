package kz.javalab.songslyricswebsite.command;

import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyricsComposite;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.exception.NoSuchSongException;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongsService;
import kz.javalab.songslyricswebsite.util.LyricsParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaperPlane on 31.08.2017.
 */
public class EditSongCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        String page = new String();

        if (user == null ||user.getUserType() != UserType.MODERATOR) {
            page = ConfigurationManager.getProperty("path.page.nopermission");
            request.getRequestDispatcher(page).forward(request, response);
            return;
        }

        try {
            int songID = Integer.valueOf(request.getParameter("songID"));

            SongsService songsService = new SongsService();

            try {
                Song song = songsService.getSongByID(songID);
                SongLyrics songLyrics = song.getLyrics();
                String youTubeLink = songsService.getYouTubeLinkBySongID(songID);
                StringBuilder featuredArtists = new StringBuilder();
                StringBuilder songGenres = new StringBuilder();

                if (song.hasFeaturedArtists()) {
                    song.getFeaturedArtists().forEach(featuredArtist -> {
                        featuredArtists.append(featuredArtist.getName());
                        featuredArtists.append(";");
                    });
                }

                if (!song.getGenres().isEmpty()) {
                    song.getGenres().forEach(genre -> {
                        songGenres.append(genre);
                        songGenres.append(";");
                    });
                }

                LyricsParser lyricsParser = new LyricsParser();

                String unparsedLyrics = lyricsParser.unparseLyrics(songLyrics);

                request.setAttribute("songArtist", song.getArtist());
                request.setAttribute("featuredArtists", featuredArtists);
                request.setAttribute("songName", song.getName());
                request.setAttribute("songGenres", songGenres);
                request.setAttribute("unparsedLyrics", unparsedLyrics);
                request.setAttribute("youTubeVideoID", youTubeLink);
                request.setAttribute("songID", songID);

                page = ConfigurationManager.getProperty("path.page.editsong");
                request.getRequestDispatcher(page).forward(request, response);
            } catch (NoSuchSongException e) {
                page = ConfigurationManager.getProperty("path.page.nosuchsong");
                request.getRequestDispatcher(page).forward(request, response);
            }

        } catch (NumberFormatException ex) {
            page = ConfigurationManager.getProperty("path.page.nosuchsong");
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}

