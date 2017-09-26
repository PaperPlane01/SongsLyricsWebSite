package kz.javalab.songslyricswebsite.command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Classes implementing <Code>ActionCommand</Code> interface are responsible for handling user's request and sending an appropriate response to it.
 */
public interface ActionCommand {

    /**
     * This method is responsible for handling user's request and sending an appropriate response to it.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
