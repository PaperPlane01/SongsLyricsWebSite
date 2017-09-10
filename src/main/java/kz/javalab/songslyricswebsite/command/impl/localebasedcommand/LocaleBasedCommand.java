package kz.javalab.songslyricswebsite.command.impl.localebasedcommand;

import com.google.gson.Gson;
import kz.javalab.songslyricswebsite.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by PaperPlane on 27.08.2017.
 */
public abstract class LocaleBasedCommand implements ActionCommand {

    @Override
    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        String languageAttribute = (String) request.getSession().getAttribute("language");

        String[] languageAndCountry = languageAttribute.split("_");

        int languageIndex = 0;
        int countryIndex = 1;

        String language = languageAndCountry[languageIndex];
        String country = languageAndCountry[countryIndex];

        return new Locale(language, country);
    }

    protected void sendJsonResponse(Object responseObject, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(responseObject);

        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
