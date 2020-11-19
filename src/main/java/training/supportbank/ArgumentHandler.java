package training.supportbank;

import java.util.Scanner;

public class ArgumentHandler {
    private static void printAvailableCommands() {
        System.out.println("Commands available:");
        System.out.println(TextHandler.getBoldText("list all") + "      (view all accounts and their balances)");
        System.out.println(TextHandler.getBoldText("list <name>") + "   (view transactions for the account belonging to <name>)");
        System.out.println(TextHandler.getBoldText("exit"));
        System.out.println();
    }

    private static boolean isValidCommand(String command) {
        String simplifiedCommand = TextHandler.simplifyStringAndMakeLowercase(command);
        boolean hasAtLeastTwoSpaceDelimitedParts = simplifiedCommand.split("\\s", 2).length == 2;

        if (simplifiedCommand.equals("exit") || simplifiedCommand.equals("quit"))
            return true;
        else if (simplifiedCommand.startsWith("list") && hasAtLeastTwoSpaceDelimitedParts)
            return true;
        else
            return false;
    }

    private static boolean isExitCommand(String command) {
        String simplifiedCommand = TextHandler.simplifyStringAndMakeLowercase(command);
        return simplifiedCommand.equals("exit") || simplifiedCommand.equals("quit");
    }

    private static boolean isListCommand(String command) {
        String simplifiedCommand = TextHandler.simplifyStringAndMakeLowercase(command);
        return simplifiedCommand.startsWith("list ");
    }

    private static void runListCommand(String command, AccountsRegister accountsRegister) {
        String simplifiedCommand = TextHandler.simplifyStringAndMakeLowercase(command);
        if (simplifiedCommand.equals("list all"))
            accountsRegister.printAllAccountsAndBalances();
        else
            accountsRegister.printTransactionsForPerson(simplifiedCommand.split("\\s", 2)[1]);
    }

    private static String requestInitialCommand(Scanner scanner) {
        System.out.println("Which command would you like to run?");
        String command = scanner.nextLine();
        System.out.println();
        return command;
    }

    private static boolean shouldDiscontinueCommandRequests(Scanner scanner) {
        System.out.println("----------------");
        System.out.println();
        System.out.println("Would you like to run another command? (y/n)");
        String response = scanner.nextLine();
        System.out.println();
        return TextHandler.simplifyString(response).startsWith("n");
    }

    public static void run(AccountsRegister accountsRegister) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printAvailableCommands();
            String command = requestInitialCommand(scanner);

            if (!isValidCommand(command))
                System.out.println("Your input \"" + command + "\" does not match any recognised commands.");
            if (isListCommand(command))
                runListCommand(command, accountsRegister);
            if (isExitCommand(command) || shouldDiscontinueCommandRequests(scanner)) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}
