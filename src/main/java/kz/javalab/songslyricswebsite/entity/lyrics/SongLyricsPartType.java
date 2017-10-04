package kz.javalab.songslyricswebsite.entity.lyrics;


import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This Enum includes types of lyrics parts and methods for converting them to string.
 */
public enum SongLyricsPartType {
    VERSE,
    CHORUS,
    BRIDGE,
    HOOK,
    INTRO,
    OUTRO,
    OTHER,
    LINE;

    /**
     * Converts lyrics part type to localized string.
     * @param locale Required locale.
     * @return Localized string value of lyrics part type.
     */
    public String toLocalizedString(Locale locale) {
        String result = new String();
        String bundle = "labels";

        String bridgeKey = "labels.songparts.bridge";
        String chorusKey = "labels.songparts.chorus";
        String hookKey = "labels.songparts.hook";
        String introKey = "labels.songparts.intro";
        String outroKey = "labels.songparts.outro";
        String verseKey = "labels.songparts.verse";

        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle, locale);

        switch (this) {
            case BRIDGE:
                result = resourceBundle.getString(bridgeKey);
                break;
            case CHORUS:
                result = resourceBundle.getString(chorusKey);
                break;
            case HOOK:
                result = resourceBundle.getString(hookKey);
                break;
            case INTRO:
                result = resourceBundle.getString(introKey);
                break;
            case OUTRO:
                result = resourceBundle.getString(outroKey);
                break;
            case VERSE:
                result = resourceBundle.getString(verseKey);
                break;
            default:
                break;
        }

        return result;
    }
}
