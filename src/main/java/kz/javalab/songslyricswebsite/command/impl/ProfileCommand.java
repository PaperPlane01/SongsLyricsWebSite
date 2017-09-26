package kz.javalab.songslyricswebsite.command.impl;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.constant.RequestConstants;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.InvalidUserIDException;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.UsersManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                    sendToMyProfilePage(request, response);
                } else {
                    try {
                        sendToProfilePage(request, response, requestedUser);
                    } catch (InvalidUserIDException e) {
                        sendToErrorPage(request, response);
                    }
                }
            } else {
                try {
                    sendToProfilePage(request, response, requestedUser);
                } catch (InvalidUserIDException e) {
                    sendToErrorPage(request, response);
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
    private void sendToMyProfilePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty(ResponseConstants.Pages.MY_PROFILE_PAGE);
        request.getRequestDispatcher(page).forward(request, response);
    }

    private void sendToProfilePage(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, InvalidUserIDException {
        String page = new String();
        UsersManager usersManager = new UsersManager();

        user.setUsername(usersManager.getUserNameByUserID(user.getID()));
        user.setUserType(usersManager.getUserTypeByUserID(user.getID()));
        request.setAttribute(RequestConstants.RequestAttributes.PROFILE_OWNER, user);

        page = ConfigurationManager.getProperty(ResponseConstants.Pages.PROFILE_PAGE);
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
        String page = ConfigurationManager.getProperty(ResponseConstants.Pages.NO_SUCH_USER_PAGE);
        request.getRequestDispatcher(page).forward(request, response);
    }
}
