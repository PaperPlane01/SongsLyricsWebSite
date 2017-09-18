package kz.javalab.songslyricswebsite.command;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.constant.ResponseConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * All classes extending <Code>JSONCommand</Code> class are supposed to send responses in JSON format.
 */
public abstract class JSONCommand implements ActionCommand {

    @Override
    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(responseData);

        response.setContentType(ResponseConstants.ContentTypes.JSON);
        response.getWriter().write(json);
    }
}
