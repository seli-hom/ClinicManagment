package org.example;

import java.util.Locale;
import java.util.ResourceBundle;

interface MessageHandler {
    String getMessage(String key);
}

class ResourceBundleMessageHandler implements MessageHandler {
    private ResourceBundle bundle;

    public ResourceBundleMessageHandler(Locale locale) {
        bundle = ResourceBundle.getBundle("org.example.MessagesBundle", locale);
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
    private static ResourceBundle bundle;

    static {
        // Default language
        bundle = ResourceBundle.getBundle("org.talia.MessagesBundle", Locale.getDefault());
    }

    public static void init(Locale locale) {
        bundle = ResourceBundle.getBundle("org.talia.MessagesBundle", locale);
    }

    public static String getMessage(String key) {
        return bundle.getString(key);
    }
}
