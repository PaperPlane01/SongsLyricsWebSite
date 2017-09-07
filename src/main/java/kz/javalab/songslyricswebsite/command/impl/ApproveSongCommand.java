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

/**
 * Created by PaperPlane on 19.08.2017.
 */
public class ApproveSongCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = new String();

        if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");

            if (user.getUserType() != UserType.MODERATOR) {
                page = ConfigurationManager.getProperty("path.page.nopermission");
            } else {
                int songID = Integer.valueOf(request.getParameter("songID"));

                SongsManager songsManager = new SongsManager();
                songsManager.approveSong(songID);

                page = ConfigurationManager.getProperty("path.page.songapproved");
            }
        } else {
            page = ConfigurationManager.getProperty("path.page.nopermission");
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}
