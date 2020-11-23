package training.supportbank;

public class TextHandler {
    private static final String BOLD = "\033[1m";
    private static final String ITALIC = "\033[3m";
    private static final String RED = "\033[31m";
    private static final String YELLOW = "\033[33m";
    private static final String GREEN = "\033[36m";
    private static final String CLEAR = "\033[0m";

    public static String getBold(String text) {
        return BOLD + text + CLEAR;
    }

    public static String getItalic(String text) {
        return ITALIC + text + CLEAR;
    }

    public static String getRed(String text) {
        return RED + text + CLEAR;
    }

    public static String getYellow(String text) {
        return YELLOW + text + CLEAR;
    }

    public static String getGreen(String text) {
        return GREEN + text + CLEAR;
    }

    public static String removeExcessSpace(String string) {
        if (string == null)
            return null;
        return string.trim().replaceAll("\\s+", " ");
    }
}
