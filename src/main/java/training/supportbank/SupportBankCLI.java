package training.supportbank;

import java.util.List;
import java.util.Scanner;

public class SupportBankCLI {
    private static final Command[] DEFAULT_COMMANDS = {
        new Command("load <filename>", "load bank data from <filename> (extension .csv, .json or .xml)"),
        new Command("list all", "view all accounts in current bank and their balances"),
        new Command("list <name>", "view transactions for account in current bank belonging to <name>"),
        new Command("exit", "close program")
    };

    public static void run() {
        DialogueHandler.logInfo("New session started!");

        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        DialogueHandler.printWelcome();
        DialogueHandler.printHorizontalRule();
        while (true) {
            DialogueHandler.printLoadedBank(bank.getSourceFile());
            DialogueHandler.printAvailableCommands(DEFAULT_COMMANDS);
            String commandInput = requestInitialCommand(scanner);
            String command = TextHandler.removeExcessSpace(commandInput);

            if (isLoadCommand(command))
                runLoadCommand(command, bank);
            else if (isListCommand(command))
                runListCommand(command, bank);
            else if (!isExitCommand(command))
                System.out.println("Your input \"" + commandInput + "\" does not match any recognised commands.");
            if (isExitCommand(command) || shouldDiscontinueCommandRequests(scanner))
                break;
        }
        System.out.println("Goodbye!");
    }

    private static boolean isLoadCommand(String command) {
        String[] commandParts = command.split("\\s", 2);
        return commandParts.length > 0 && commandParts[0].equalsIgnoreCase("load");
    }

    private static boolean isListCommand(String command) {
        String[] commandParts = command.split("\\s", 2);
        return commandParts.length > 0 && commandParts[0].equalsIgnoreCase("list");
    }

    private static boolean isExitCommand(String command) {
        return command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit");
    }

    private static void runLoadCommand(String command, Bank bank) {
        String[] commandParts = command.split("\\s", 2);
        String filename = commandParts[commandParts.length - 1];
        Parser parser = null;
        List<Transaction> transactionTable = null;

        if (commandParts.length == 1)
            DialogueHandler.outputError("Please include a filename after the word \"load\" to indicate which file " +
                    "the bank data should be loaded from.");
        else if (hasExtension(filename, "csv"))
            parser = new CSVParser(filename);
        else if (hasExtension(filename, "json"))
            parser = new JSONParser(filename);
        else if (hasExtension(filename, "xml"))
            parser = new XMLParser(filename);
        else
            DialogueHandler.outputError("\"" + filename + "\" is not a valid name for a CSV, JSON or XML file.\nAny " +
                    "such filename should consist of at least one character followed by a .csv, .json or .xml " +
                    "extension.\n(Example: " + TextHandler.getItalic("Transactions2014.csv") + ")");
        if (parser == null)
            return;
        transactionTable = parser.getParsedTransactionTableFromFile();
        if (transactionTable != null) {
            bank.setSourceFile(filename);
            bank.setTransactions(transactionTable);
            bank.updateAccounts();
            DialogueHandler.outputInfo(TextHandler.getBold(filename) + " loaded successfully.");
        }
        else if (bank.getSourceFile() != null)
            DialogueHandler.outputInfo("Since a new bank was not loaded successfully, " +
                    TextHandler.getBold(bank.getSourceFile()) + " has been kept open.");
    }

    private static void runListCommand(String command, Bank bank) {
        String[] commandParts = command.split("\\s", 2);

        if (bank.getSourceFile() == null)
            System.out.println("Please load a bank before attempting to list accounts or transactions.");
        else if (commandParts.length == 1)
            System.out.println("Please include another instruction after the word \"list\" to indicate what " +
                    "information should be displayed. You can view your options in the summary of available commands.");
        else if (command.equalsIgnoreCase("list all"))
            bank.printAllAccountsAndBalances();
        else
            bank.printTransactionsForPerson(commandParts[1]);
    }

    private static String requestInitialCommand(Scanner scanner) {
        System.out.println("Which command would you like to run?");
        String command = scanner.nextLine();
        System.out.println();
        return command;
    }

    private static boolean shouldDiscontinueCommandRequests(Scanner scanner) {
        DialogueHandler.printHorizontalRule();
        System.out.println("Would you like to run another command? (y/n)");
        String response = scanner.nextLine();
        System.out.println();
        return TextHandler.removeExcessSpace(response).toLowerCase().startsWith("n");
    }

    private static boolean hasExtension(String filename, String extension) {
        int positionOfFinalPoint = filename.lastIndexOf('.');
        String thisFileExtension = filename.substring(positionOfFinalPoint + 1);
        String desiredExtension = extension.substring(extension.indexOf('.') + 1);

        if (positionOfFinalPoint < 1 || positionOfFinalPoint == filename.length() - 1)
            return false;
        return thisFileExtension.equalsIgnoreCase(desiredExtension);
    }
}
