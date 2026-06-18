package org.example.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Messages {
    private static final String BUNDLE_NAME = "i18n.messages";
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());

    private Messages() {
    }

    public static String get(String key, Object... args) {
        String pattern = resolve(key);
        if (args == null || args.length == 0) {
            return pattern;
        }
        return MessageFormat.format(pattern, args);
    }

    private static String resolve(String key) {
        try {
            return BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return "??" + key + "??";
        }
    }
}
