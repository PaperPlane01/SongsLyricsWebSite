package kz.javalab.songslyricswebsite.command.impl;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongsManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 *
 */
public class NotApprovedSongsCommand implements ActionCommand {

    public NotApprovedSongsCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = new String();

        if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");

            if (user.getUserType() != UserType.MODERATOR) {
                page = ConfigurationManager.getProperty("path.page.nopermission");
            } else {
                SongsManager songsManager = new SongsManager();
                boolean notApproved = false;

                Map<Integer, String> songsIDsWithTitles = songsManager.getSongIDsWithTitles(notApproved);
                request.setAttribute("songsIDsAndTitles", songsIDsWithTitles);

                page = ConfigurationManager.getProperty("path.page.songs");
            }
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}
