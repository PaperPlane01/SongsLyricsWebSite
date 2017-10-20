package kz.javalab.songslyricswebsite.entity.user;

import java.util.Locale;
import java.util.ResourceBundle;

public enum UserType {
    COMMON,
    MODERATOR;

    public String toLocalizedString(Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", locale);
        String moderatorKey = "labels.userroles.moderator";
        String commonUserKey = "labels.userroles.common";

        String result = "";

        switch (this) {
            case MODERATOR:
                result = resourceBundle.getString(moderatorKey);
                break;
            case COMMON:
                result = resourceBundle.getString(commonUserKey);
                break;
        }

        return result;
    }
}
