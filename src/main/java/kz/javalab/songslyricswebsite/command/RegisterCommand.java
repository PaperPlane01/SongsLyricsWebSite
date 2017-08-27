package kz.javalab.songslyricswebsite.command;

import com.sun.deploy.association.RegisterFailedException;
import kz.javalab.songslyricswebsite.entity.password.Password;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.exception.RegistrationFailedException;
import kz.javalab.songslyricswebsite.exception.SuchUserAlreadyExistsException;
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

        try {
            userService.registerNewUser(user);
            page = ConfigurationManager.getProperty("path.page.index");
        } catch (SuchUserAlreadyExistsException e) {
            page = ConfigurationManager.getProperty("path.page.registrationfailed");
        } catch (RegistrationFailedException e) {
            page = ConfigurationManager.getProperty("path.page.registrationfailed");
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}
