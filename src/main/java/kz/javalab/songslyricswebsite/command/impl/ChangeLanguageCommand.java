package kz.javalab.songslyricswebsite.command.impl;

import kz.javalab.songslyricswebsite.command.ActionCommand;
import kz.javalab.songslyricswebsite.constant.RequestConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * This class is responsible for changing language.
 */
public class ChangeLanguageCommand implements ActionCommand {

    public ChangeLanguageCommand() {
    }

    /**
     * Changes language.
     * @param request Request to be handled.
     * @param response Response to be sent.
     * @throws ServletException Thrown if there is a server problem.
     * @throws IOException Thrown if some error occurred when attempted to send response.
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String localeAsString = request.getParameter(RequestConstants.RequestParameters.LANGUAGE);

        int languageIndex = 0;
        int countryIndex = 1;

        String[] languageAndCountry = localeAsString.split("_");

        Locale language = new Locale(languageAndCountry[languageIndex], languageAndCountry[countryIndex]);

        request.getSession().setAttribute(RequestConstants.SessionAttributes.LANGUAGE, language);
    }
}
