package kz.javalab.songslyricswebsite.command;

import kz.javalab.songslyricswebsite.model.password.Password;
import kz.javalab.songslyricswebsite.model.user.User;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PaperPlane on 12.08.2017.
 */
public class RegisterCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = new String();

        User user = new User();

        String userName = request.getParameter("username");
        user.setUsername(userName);

        Password password = new Password();
        password.encodePassword(request.getParameter("password"));
        user.setPassword(password);

        UserService userService = new UserService();

        if (userService.checkIfUserExists(user)) {
            page = ConfigurationManager.getProperty("path.page.registrationfailed");
        } else {
            userService.registerNewUser(user);
            page = ConfigurationManager.getProperty("path.page.index");
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}
