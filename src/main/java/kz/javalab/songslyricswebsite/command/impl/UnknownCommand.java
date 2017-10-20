package kz.javalab.songslyricswebsite.command.impl;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;
import kz.javalab.songslyricswebsite.resource.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is responsible for handling unknown or empty commands.
 */
public class UnknownCommand implements ActionCommand {

    public UnknownCommand() {
    }

    /**
     * Handles unknown or empty command and sends the user to the error page.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Config.getProperty(ResponseConstants.Pages.PAGE_NOT_FOUND);

        request.getRequestDispatcher(page).forward(request, response);
    }
}
