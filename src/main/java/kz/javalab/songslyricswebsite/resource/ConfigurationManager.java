package kz.javalab.songslyricswebsite.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by PaperPlane on 07.08.2017.
 */
public class ConfigurationManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    private ConfigurationManager() {

    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
