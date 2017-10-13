package kz.javalab.songslyricswebsite.command.impl;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.entity.song.Song;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.DataAccessException;
import kz.javalab.songslyricswebsite.exception.InvalidUserIDException;
import kz.javalab.songslyricswebsite.resource.Config;
import kz.javalab.songslyricswebsite.service.CommentsManager;
import kz.javalab.songslyricswebsite.service.SongsManager;
import kz.javalab.songslyricswebsite.service.UsersManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible to send the user to profile or personal page.
 */
public class ProfileCommand implements ActionCommand {

    public ProfileCommand() {
    }

    /**
     * Sends user to profile page of the user with the specified ID, or to the personal page, or to the error page.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userID = Integer.valueOf(request.getParameter(RequestConstants.RequestParameters.USER_ID));

            User requestedUser = new User();
            requestedUser.setID(userID);

            User sessionUser = (User) request.getSession().getAttribute(RequestConstants.SessionAttributes.USER);

            if (sessionUser != null) {
                if (requestedUser.getID() == sessionUser.getID()) {
                    try {
                        sendToMyProfilePage(request, response);
                    } catch (DataAccessException e) {
                        sendToDataLoadingErrorPage(request, response);
                    }
                } else {
                    try {
                        sendToProfilePage(request, response, requestedUser);
                    } catch (InvalidUserIDException e) {
                        sendToErrorPage(request, response);
                    } catch (DataAccessException e) {
                        sendToDataLoadingErrorPage(request, response);
                    }
                }
            } else {
                try {
                    sendToProfilePage(request, response, requestedUser);
                } catch (InvalidUserIDException e) {
                    sendToErrorPage(request, response);
                } catch (DataAccessException e) {
                    sendToDataLoadingErrorPage(request, response);;
                }
            }
        } catch (NumberFormatException e) {
            sendToErrorPage(request, response);
        }


    }

    /**
     * Sends the user to the personal page.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    private void sendToMyProfilePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataAccessException {
        User user = (User) request.getSession().getAttribute(RequestConstants.SessionAttributes.USER);
        SongsManager songsManager = new SongsManager();
        CommentsManager commentsManager = new CommentsManager();

        List<Song> songsContributedByUser = songsManager.getSongsContributedByUser(user.getID());
        int numberOfComments = commentsManager.getNumberOfCommentsOfUser(user.getID());

        request.setAttribute(RequestConstants.RequestAttributes.CONTRIBUTED_SONGS, songsContributedByUser);
        request.setAttribute(RequestConstants.RequestAttributes.NUMBER_OF_COMMENTS, numberOfComments);

        String page = Config.getProperty(ResponseConstants.Pages.MY_PROFILE_PAGE);
        request.getRequestDispatcher(page).forward(request, response);
    }

    /**
     * Sends user to profile page of the specified user.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @param user Profile owner.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     * @throws InvalidUserIDException Thrown if user ID is invalid.
     */
    private void sendToProfilePage(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, InvalidUserIDException, DataAccessException {
        String page = new String();
        UsersManager usersManager = new UsersManager();
        SongsManager songsManager = new SongsManager();
        CommentsManager commentsManager = new CommentsManager();

        user.setUsername(usersManager.getUserNameByUserID(user.getID()));
        user.setUserType(usersManager.getUserTypeByUserID(user.getID()));
        List<Song> songsContributedByUser = songsManager.getSongsContributedByUser(user.getID());
        int numberOfComments = commentsManager.getNumberOfCommentsOfUser(user.getID());

        request.setAttribute(RequestConstants.RequestAttributes.PROFILE_OWNER, user);
        request.setAttribute(RequestConstants.RequestAttributes.CONTRIBUTED_SONGS, songsContributedByUser);
        request.setAttribute(RequestConstants.RequestAttributes.NUMBER_OF_COMMENTS, numberOfComments);

        page = Config.getProperty(ResponseConstants.Pages.PROFILE_PAGE);
        request.getRequestDispatcher(page).forward(request, response);
    }

    /**
     * Sends the user to the error page.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    private void sendToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Config.getProperty(ResponseConstants.Pages.NO_SUCH_USER_PAGE);
        request.getRequestDispatcher(page).forward(request, response);
    }

    private void sendToDataLoadingErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Config.getProperty(ResponseConstants.Pages.DATA_LOADING_ERROR_PAGE);
        request.getRequestDispatcher(page).forward(request, response);
    }
}
