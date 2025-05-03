package org.example;

import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public final class Messages {
    private static final String BASE = "org.example.MessagesBundle";
    private static ResourceBundle bundle;

    static {
        try {
            bundle = ResourceBundle.getBundle(
                    BASE,
                    Locale.getDefault(),
                    Messages.class.getClassLoader()
            );
        } catch (Exception e) {
            // fallback to manual load if ResourceBundle fails
            bundle = null;
        }
    }

    private Messages() {}

    public static void init(Locale locale) {
        bundle = ResourceBundle.getBundle(
                BASE,
                locale,
                Messages.class.getClassLoader()
        );
    }

    public static String getMessage(String key) {
        if (bundle != null) {
            try {
                return bundle.getString(key);
            } catch (Exception ignored) {}
        }

        // ultimate fallback: load raw .properties
        try (InputStream in = Messages.class.getClassLoader()
                .getResourceAsStream("org/example/MessagesBundle.properties")) {
            if (in != null) {
                Properties p = new Properties();
                p.load(in);
                return p.getProperty(key, "!" + key + "!");
            }
        } catch (Exception ex) {
            // swallow
        }
        return "!" + key + "!";
    }
}
