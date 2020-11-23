package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DialogueHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int COMMAND_FIELD_WIDTH = 26;

    public static void outputError(String message) {
        System.out.println(TextHandler.getRed("Error. ") + message);
        LOGGER.error(message);
    }

    public static void outputWarning(String message) {
        System.out.println(TextHandler.getYellow("Warning. ") + message);
        LOGGER.warn(message);
    }

    public static void outputInfo(String message) {
        System.out.println(TextHandler.getGreen("Info. ") + message);
        LOGGER.info(message);
    }

    public static void logInfo(String message) {
        LOGGER.info(message);
    }

    public static void printHorizontalRule() {
        System.out.println("--------------------");
    }

    public static void printWelcome() {
        System.out.println();
        System.out.println("Welcome to SupportBank v1.0!");
    }

    public static void printLoadedBank(String sourceFile) {
        if (sourceFile == null) {
            System.out.println(TextHandler.getBold("No bank currently loaded."));
            System.out.println("Please select a CSV, JSON or XML file with transaction data to load a bank from.");
        }
        else
            System.out.println("Currently working with bank loaded from: " + TextHandler.getBold(sourceFile));
        System.out.println();
    }

    public static void printAvailableCommands(Command[] commands) {
        System.out.println("Commands available:");
        for (Command command: commands)
            System.out.printf("%-" + COMMAND_FIELD_WIDTH + "s | %s\n",
                    TextHandler.getBold(command.getText()), TextHandler.getItalic(command.getDescription()));
        System.out.println();
    }

    public static void printTransactionErrorHeading() {
        System.out.println("Some transactions in the file contain errors. Details:");
    }

    public static void printTransactionWarningHeading(boolean hasErrors) {
        if (hasErrors)
            System.out.println();
        System.out.println("Some transactions in the file " +
                (hasErrors ? "also " : "") +
                "raised warnings. Details:");
    }

    public static void printTransactionErrorNote() {
        System.out.println();
        System.out.println("Transactions containing any errors have not been loaded.");
        System.out.println();
    }

    public static void outputTransactionNotifications(List<String> errors, List<String> warnings) {
        if (errors.size() > 0) {
            DialogueHandler.printTransactionErrorHeading();
            for (String error: errors)
                DialogueHandler.outputError(error);
        }
        if (warnings.size() > 0) {
            DialogueHandler.printTransactionWarningHeading(errors.size() > 0);
            for (String warning: warnings)
                DialogueHandler.outputWarning(warning);
        }
        if (errors.size() > 0)
            DialogueHandler.printTransactionErrorNote();
    }

    public static void outputIOExceptionMessage(String filename) {
        DialogueHandler.outputError("Could not read from \"" + filename + "\": " +
                "file permissions might be too restrictive or reading may have been interrupted.");
    }

    public static void outputJSONParseExceptionMessage(String filename) {
        DialogueHandler.outputError("Could not interpret JSON data from \"" + filename + "\": " +
                "file content may not be valid JSON. Please ensure content is formatted correctly.");
    }

    public static void outputXMLParseExceptionMessage(String filename) {
        DialogueHandler.outputError("Could not interpret XML data from \"" + filename + "\": " +
                "file content may not be valid XML. Please ensure content is formatted correctly.");
    }

    public static void outputEmptyDataMessage(String filename) {
        DialogueHandler.outputError("\"" + filename + "\" does not appear to contain any valid transaction data.");
    }
}
