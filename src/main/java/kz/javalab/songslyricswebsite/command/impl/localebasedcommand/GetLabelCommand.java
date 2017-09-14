package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

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

    public GetLabelCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Locale locale = getLocaleFromRequest(request);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", locale);

        String labelKey = request.getParameter("labelKey");

        String message = resourceBundle.getString(labelKey);

        sendJsonResponse(message, response);
    }

    @Override
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return super.getLocaleFromRequest(request);
    }

    @Override
    protected void sendJsonResponse(Object responseObject, HttpServletResponse response) throws IOException {
        super.sendJsonResponse(responseObject, response);
    }
}
