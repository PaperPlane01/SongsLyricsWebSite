package kz.javalab.songslyricswebsite.command.impl;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for sending user to the page with the list of not approved songs.
 */
public class NotApprovedSongsCommand implements ActionCommand {

    public NotApprovedSongsCommand() {
    }

    /**
     * Sends user to the page with the list of not approved songs or to the error page if user doesn't have a permission.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = new String();

        if (request.getSession().getAttribute(RequestConstants.SessionAttributes.USER) != null) {
            User user = (User) request.getSession().getAttribute(RequestConstants.SessionAttributes.USER);

            if (user.getUserType() != UserType.MODERATOR) {
                page = ConfigurationManager.getProperty(ResponseConstants.Pages.NO_PERMISSION_PAGE);
            } else {
                SongsManager songsManager = new SongsManager();

                List<Song> notApprovedSongs = songsManager.getListOfNotApprovedSongs();
                request.setAttribute(RequestConstants.RequestAttributes.NOT_APPROVED_SONGS, notApprovedSongs);

                page = ConfigurationManager.getProperty(ResponseConstants.Pages.NOT_APPROVED_SONGS_PAGE);
            }
        } else {
            page = ConfigurationManager.getProperty(ResponseConstants.Pages.NO_PERMISSION_PAGE);
        }
        request.getRequestDispatcher(page).forward(request, response);
    }
}
