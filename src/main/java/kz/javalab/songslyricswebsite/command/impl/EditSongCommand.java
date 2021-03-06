package kz.javalab.songslyricswebsite.command.impl;

import com.sun.org.apache.regexp.internal.RE;
import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.entity.artist.Artist;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.exception.DataAccessException;
import kz.javalab.songslyricswebsite.exception.NoSuchSongException;
import kz.javalab.songslyricswebsite.resource.Config;
import kz.javalab.songslyricswebsite.service.SongsManager;
import kz.javalab.songslyricswebsite.util.LyricsParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is responsible for displaying page of editing song.
 */
public class EditSongCommand implements ActionCommand {

    private final String SEPARATOR = ";";

    public EditSongCommand() {
    }

    /**
     * Sends user to the page of editing song or informs the user that he or she does not have an access to this page.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestConstants.SessionAttributes.USER);

        String page = "";

        if (user == null || user.getUserType() != UserType.MODERATOR) {
            page = Config.getProperty(ResponseConstants.Pages.NO_PERMISSION_PAGE);
            request.getRequestDispatcher(page).forward(request, response);
            return;
        }

        try {
            int songID = Integer.valueOf(request.getParameter(RequestConstants.RequestParameters.SONG_ID));

            SongsManager songsManager = new SongsManager();

            try {
                Song song = songsManager.getSongByID(songID);
                SongLyrics songLyrics = song.getLyrics();
                String youTubeLink = song.getYouTubeVideoID();
                StringBuilder featuredArtists = new StringBuilder();
                StringBuilder songGenres = new StringBuilder();

                if (song.hasFeaturedArtists()) {
                    for (Artist featuredArtist : song.getFeaturedArtists()) {
                        featuredArtists.append(featuredArtist.getName());
                        featuredArtists.append(SEPARATOR);
                    }
                }

                if (!song.getGenres().isEmpty()) {
                    for (String genre : song.getGenres()) {
                        songGenres.append(genre);
                        songGenres.append(SEPARATOR);
                    }
                }

                LyricsParser lyricsParser = new LyricsParser();

                String unparsedLyrics = lyricsParser.unparseLyrics(songLyrics);

                request.setAttribute(RequestConstants.RequestAttributes.SONG_ARTIST, song.getArtist());
                request.setAttribute(RequestConstants.RequestAttributes.FEATURED_ARTISTS, featuredArtists);
                request.setAttribute(RequestConstants.RequestAttributes.SONG_NAME, song.getName());
                request.setAttribute(RequestConstants.RequestAttributes.SONG_GENRES, songGenres);
                request.setAttribute(RequestConstants.RequestAttributes.SONG_LYRICS, unparsedLyrics);
                request.setAttribute(RequestConstants.RequestAttributes.YOUTUBE_VIDEO_ID, youTubeLink);
                request.setAttribute(RequestConstants.RequestAttributes.SONG_ID, songID);

                page = Config.getProperty(ResponseConstants.Pages.EDIT_SONG_PAGE);
                request.getRequestDispatcher(page).forward(request, response);
            } catch (NoSuchSongException e) {
                page = Config.getProperty(ResponseConstants.Pages.NO_SUCH_SONG_PAGE);
                request.getRequestDispatcher(page).forward(request, response);
            } catch (DataAccessException e) {
                page = Config.getProperty(ResponseConstants.Pages.DATA_LOADING_ERROR_PAGE);
                request.getRequestDispatcher(page).forward(request, response);
            }

        } catch (NumberFormatException ex) {
            page = Config.getProperty(ResponseConstants.Pages.NO_SUCH_SONG_PAGE);
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}

