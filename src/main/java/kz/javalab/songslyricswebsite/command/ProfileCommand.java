package kz.javalab.songslyricswebsite.command;

import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.InvalidUserIDException;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PaperPlane on 26.08.2017.
 */
public class ProfileCommand implements ActionCommand {

    public ProfileCommand() {

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userID = Integer.valueOf(request.getParameter("userID"));

            User requestedUser = new User();
            requestedUser.setID(userID);

            User sessionUser = (User) request.getSession().getAttribute("user");

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

    private void sendToMyProfilePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty("path.page.myprofile");
        request.getRequestDispatcher(page).forward(request, response);
    }

    private void sendToProfilePage(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, InvalidUserIDException {
        String page = new String();
        UserService userService = new UserService();

        user.setUsername(userService.getUserNameByUserID(user.getID()));
        user.setUserType(userService.getUserTypeByUserID(user.getID()));
        request.setAttribute("profileOwner", user);

        page = ConfigurationManager.getProperty("path.page.profile");
        request.getRequestDispatcher(page).forward(request, response);
    }

    private void sendToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty("path.page.nosuchuser");
        request.getRequestDispatcher(page).forward(request, response);
    }
}
