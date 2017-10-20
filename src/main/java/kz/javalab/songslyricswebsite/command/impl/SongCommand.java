package kz.javalab.songslyricswebsite.command.impl;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.entity.comment.Comment;
import kz.javalab.songslyricswebsite.entity.lyrics.SongLyrics;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.DataAccessException;
import kz.javalab.songslyricswebsite.exception.NoSuchSongException;
import kz.javalab.songslyricswebsite.resource.Config;
import kz.javalab.songslyricswebsite.service.CommentsManager;
import kz.javalab.songslyricswebsite.service.SongsManager;
import kz.javalab.songslyricswebsite.service.SongsRatingsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for sending the user to the page with the specific song.
 */
public class SongCommand implements ActionCommand {

    public SongCommand() {
    }

    /**
     * Sends the user to the page of the song with the specific ID, or to the error page.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int songID = Integer.valueOf(request.getParameter(RequestConstants.RequestParameters.SONG_ID));

            SongsManager songsManager = new SongsManager();
            CommentsManager commentsManager = new CommentsManager();


            try {
                Song song = songsManager.getSongByID(songID);
                SongLyrics songLyrics = song.getLyrics();
                String songTitle = song.getTitle();
                String youTubeLink = song.getYouTubeVideoID();
                Boolean isApproved = song.isApproved();

                List<SongLyrics> lyricsPartsAsList = new ArrayList<>();

                for (SongLyrics songPart : songLyrics.getComponents()) {
                    lyricsPartsAsList.add(songPart);
                }

                request.setAttribute(RequestConstants.RequestAttributes.SONG_TITLE, songTitle);
                request.setAttribute(RequestConstants.RequestAttributes.LIST_OF_LYRICS_PARTS, lyricsPartsAsList);
                request.setAttribute(RequestConstants.RequestAttributes.YOUTUBE_VIDEO_ID, youTubeLink);
                request.setAttribute(RequestConstants.RequestAttributes.IS_APPROVED, isApproved);
                request.setAttribute(RequestConstants.RequestAttributes.SONG_ID, songID);
                request.setAttribute(RequestConstants.RequestAttributes.SONG, song);

                if (commentsManager.checkIfSongHasComments(songID)) {
                    List<Comment> comments = commentsManager.getCommentsOfSong(songID);
                    request.setAttribute(RequestConstants.RequestAttributes.COMMENTS, comments);
                }

                if (request.getSession().getAttribute(RequestConstants.SessionAttributes.USER) != null) {
                    User currentUser = (User) request.getSession().getAttribute(RequestConstants.SessionAttributes.USER);

                    SongsRatingsManager songsRatingsManager = new SongsRatingsManager();

                    Boolean userHasRatedSong = songsRatingsManager.checkIfUserRatedSong(currentUser.getID(), song.getID());

                    request.setAttribute(RequestConstants.RequestAttributes.USER_HAS_RATED_SONG, userHasRatedSong);

                    if (userHasRatedSong) {
                        int userRatingOfSong = songsRatingsManager.getUserRatingOfSong(currentUser.getID(), song.getID());
                        request.setAttribute(RequestConstants.RequestAttributes.USER_RATING, userRatingOfSong);
                    }
                }

                String page = Config.getProperty(ResponseConstants.Pages.SONG_PAGE);
                request.getRequestDispatcher(page).forward(request, response);
            } catch (NoSuchSongException e) {
                String page = Config.getProperty(ResponseConstants.Pages.NO_SUCH_SONG_PAGE);
                request.getRequestDispatcher(page).forward(request, response);
            } catch (DataAccessException e) {
                String page = Config.getProperty(ResponseConstants.Pages.DATA_LOADING_ERROR_PAGE);
                request.getRequestDispatcher(page).forward(request, response);
            }

        } catch (NumberFormatException ex) {
            String page = Config.getProperty(ResponseConstants.Pages.NO_SUCH_SONG_PAGE);
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
