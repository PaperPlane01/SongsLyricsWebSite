package kz.javalab.songslyricswebsite.resource;

import java.util.ResourceBundle;

/**
 * Created by PaperPlane on 08.08.2017.
 */
public class LabelsManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("labels");

    private LabelsManager() {

    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

}
