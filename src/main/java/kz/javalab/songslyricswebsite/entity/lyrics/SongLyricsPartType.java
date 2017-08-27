package kz.javalab.songslyricswebsite.entity.lyrics;


import java.util.Locale;
import java.util.ResourceBundle;

public enum SongLyricsPartType {
    WHOLE_SONG,
    VERSE,
    CHORUS,
    BRIDGE,
    HOOK,
    INTRO,
    OUTRO,
    OTHER,
    LINE;

    public String toString(String locale) {
        String result = "";

        String[] languageAndCountry = locale.split("_");

        int languageIndex = 0;
        int countryIndex = 1;

        String language = languageAndCountry[languageIndex];
        String country = languageAndCountry[countryIndex];

        Locale currentLocale = new Locale(language, country);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", currentLocale);

        if (this.equals(VERSE)) {
            String key = "labels.songparts.verse";
            result = resourceBundle.getString(key);
        }

        if (this.equals(CHORUS)) {
            String key = "labels.songparts.chorus";
            result = resourceBundle.getString(key);
        }

        if (this.equals(BRIDGE)) {
            String key = "labels.songparts.bridge";
            result = resourceBundle.getString(key);
        }

        if (this.equals(HOOK)) {
            String key = "labels.songparts.hook";
            result = resourceBundle.getString(key);
        }

        if (this.equals(INTRO)) {
            String key = "labels.songparts.intro";
            result = resourceBundle.getString(key);
        }

        if (this.equals(OUTRO)) {
            String key = "labels.songparts.outro";
            result = resourceBundle.getString(key);
        }

        return result;
    }
}
