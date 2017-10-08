package kz.javalab.songslyricswebsite.resource;

import java.util.ResourceBundle;

/**
 * This class is designated for providing access to "config" resource bundle.
 */
public class Config {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    private Config() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
