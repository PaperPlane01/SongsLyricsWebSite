package kz.javalab.songslyricswebsite.command;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PaperPlane on 16.09.2017.
 */
public abstract class JSONCommand implements ActionCommand {

    @Override
    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(responseData);

        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
