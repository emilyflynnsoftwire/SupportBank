package training.supportbank;

public class TextHandler {
    private static final String BOLD = "\033[1m";
    private static final String CLEAR = "\033[0m";

    public static String getBoldText(String text) {
        return BOLD + text + CLEAR;
    }

    public static String removeExcessSpace(String string) {
        if (string == null)
            return null;
        return string.trim().replaceAll("\\s+", " ");
    }
}