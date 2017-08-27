package kz.javalab.songslyricswebsite.command.localebasedcommand;

import kz.javalab.songslyricswebsite.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by PaperPlane on 27.08.2017.
 */
public abstract class LocaleBasedCommand implements ActionCommand {

    protected Locale getLocaleFromRequest(HttpServletRequest request) {
        String languageAttribute = (String) request.getSession().getAttribute("language");

        String[] languageAndCountry = languageAttribute.split("_");

        int languageIndex = 0;
        int countryIndex = 1;

        String language = languageAndCountry[languageIndex];
        String country = languageAndCountry[countryIndex];

        return new Locale(language, country);
    }
}
