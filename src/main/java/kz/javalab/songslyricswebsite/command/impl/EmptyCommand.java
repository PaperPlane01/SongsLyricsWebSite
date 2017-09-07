package kz.javalab.songslyricswebsite.command.impl;

import com.sun.deploy.net.HttpRequest;
import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PaperPlane on 07.08.2017.
 */
public class EmptyCommand implements ActionCommand {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty("path.page.index");

        request.getRequestDispatcher(page).forward(request, response);
    }
}
