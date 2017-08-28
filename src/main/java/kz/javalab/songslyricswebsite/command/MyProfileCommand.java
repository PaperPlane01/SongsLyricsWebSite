package kz.javalab.songslyricswebsite.command;

import kz.javalab.songslyricswebsite.resource.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PaperPlane on 16.08.2017.
 */
public class MyProfileCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty("path.page.myprofile");

        request.getRequestDispatcher(page).forward(request, response);
    }
}
