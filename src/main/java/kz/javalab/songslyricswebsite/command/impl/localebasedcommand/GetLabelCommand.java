package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by PaperPlane on 27.08.2017.
 */
public class GetLabelCommand extends LocaleBasedCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Locale locale = getLocaleFromRequest(request);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", locale);

        String labelKey = request.getParameter("labelKey");

        String messageValue = resourceBundle.getString(labelKey);

        String responseLabel = new Gson().toJson(messageValue);

        response.setContentType("application/json");
        response.getWriter().write(responseLabel);
    }

    @Override
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return super.getLocaleFromRequest(request);
    }
}
