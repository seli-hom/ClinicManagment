package org.example;

import java.util.Locale;
import java.util.ResourceBundle;

interface MessageHandler {
    String getMessage(String key);
}

class ResourceBundleMessageHandler implements MessageHandler {
    private ResourceBundle bundle;

    public ResourceBundleMessageHandler(Locale locale) {
        bundle = ResourceBundle.getBundle("MessagesBundle", locale);
    }

    public String getMessage(String key) {
        return bundle.getString(key);
    }
}

// 2. Implement the Message Handler class
public class MessageHandlerFactory {
    public static MessageHandler createMessage(Locale locale) {
        return new ResourceBundleMessageHandler(locale);
    }
}
class Messages {
    private static final String BASE_NAME = "MessagesBundle";
    private static ResourceBundle bundle =
            ResourceBundle.getBundle(BASE_NAME, Locale.getDefault());

    // To switch at runtime
    public static void init(Locale locale) {
        bundle = ResourceBundle.getBundle(BASE_NAME, locale);
    }

    public static String getMessage(String key) {
        return bundle.getString(key);
    }
}
