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
        boolean hasAtLeastTwoSpaceDelimitedParts = command.split("\\s", 2).length == 2;

        if (command.equals("exit") || command.equals("quit"))
            return true;
        else if (command.startsWith("list") && hasAtLeastTwoSpaceDelimitedParts)
            return true;
        else
            return false;
    }

    private static boolean isExitCommand(String command) {
        return command.equals("exit") || command.equals("quit");
    }

    private static boolean isListCommand(String command) {
        return command.startsWith("list ");
    }

    private static void runListCommand(String command, AccountsRegister accountsRegister) {
        if (command.equals("list all"))
            accountsRegister.printAllAccountsAndBalances();
        else
            accountsRegister.printTransactionsForPerson(command.split("\\s", 2)[1]);
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
        return TextHandler.removeExcessSpace(response).toLowerCase().startsWith("n");
    }

    public static void run(AccountsRegister accountsRegister) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printAvailableCommands();
            String commandInput = requestInitialCommand(scanner);
            String command = TextHandler.removeExcessSpace(commandInput).toLowerCase();

            if (!isValidCommand(command))
                System.out.println("Your input \"" + commandInput + "\" does not match any recognised commands.");
            if (isListCommand(command))
                runListCommand(command, accountsRegister);
            if (isExitCommand(command) || shouldDiscontinueCommandRequests(scanner)) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}
