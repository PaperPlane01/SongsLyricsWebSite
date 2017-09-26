package kz.javalab.songslyricswebsite.command;


import kz.javalab.songslyricswebsite.constant.RequestConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Classes extending <Code>LocaleBasedCommand</Code> are responsible for sending responses in JSON format in language chosen by user.
 */
public abstract class LocaleBasedCommand extends JSONCommand {

    public LocaleBasedCommand() {
    }

    @Override
    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    /**
     * Retrieves locale from request.
     * @param request Request to be handled.
     * @return <Code>Locale</Code> object retrieved from request.
     */
    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        return (Locale) request.getSession().getAttribute(RequestConstants.SessionAttributes.LANGUAGE);
    }

    @Override
    protected void sendJsonResponse(Object responseData, HttpServletResponse response) throws IOException {
       super.sendJsonResponse(responseData, response);
    }
}
