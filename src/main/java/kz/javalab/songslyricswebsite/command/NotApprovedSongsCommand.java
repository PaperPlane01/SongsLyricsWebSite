package kz.javalab.songslyricswebsite.command;

import kz.javalab.songslyricswebsite.entity.user.User;
import kz.javalab.songslyricswebsite.entity.user.UserType;
import kz.javalab.songslyricswebsite.resource.ConfigurationManager;
import kz.javalab.songslyricswebsite.service.SongsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by PaperPlane on 20.08.2017.
 */
public class NotApprovedSongsCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = new String();

        if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");

            if (user.getUserType() != UserType.MODERATOR) {
                page = ConfigurationManager.getProperty("path.page.nopermission");
            } else {
                SongsService songsService = new SongsService();
                boolean notApproved = false;

                Map<Integer, String> songsIDsWithTitles = songsService.getSongIDsWithTitles(notApproved);
                request.setAttribute("songsIDsAndTitles", songsIDsWithTitles);

                page = ConfigurationManager.getProperty("path.page.songs");
            }
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}
