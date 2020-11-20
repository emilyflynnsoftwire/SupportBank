package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;

public class AccountsRegister {
    private static final Logger LOGGER = LogManager.getLogger();

    private final HashMap<String, Account> htAccounts = new HashMap<String, Account>();

    public AccountsRegister(List<String[]> transactionsTable) {
        LOGGER.info("Attempting to create a register of accounts");
        boolean errorFreeSoFar = true;

        for (int index = 0; index < transactionsTable.size(); index++) {
            String[] transactionRow = transactionsTable.get(index);
            if (isConvertibleToTransaction(transactionRow)) {
                LOGGER.info("Adding accounts for transaction participants if not already existing");
                String sender = TextHandler.removeExcessSpace(transactionRow[1]);
                String recipient = TextHandler.removeExcessSpace(transactionRow[2]);

                createAccountIfNotExisting(sender);
                createAccountIfNotExisting(recipient);

                Transaction currentTransaction = new Transaction(transactionRow);
                getAccount(sender).addOutgoingTransaction(currentTransaction);
                getAccount(recipient).addIncomingTransaction(currentTransaction);
            }
            else if (index > 0 || !isProbableHeaderRow(transactionRow)) {
                LOGGER.error("A transaction could not be created for row " + Arrays.toString(transactionRow));
                if (errorFreeSoFar)
                    System.out.println("Issues encountered in data:");
                errorFreeSoFar = false;
                displayErrorForRow(transactionRow, index);
            }
        }

        if (!errorFreeSoFar)
            warnErroneousRowsIgnored();
    }

    private void createAccountIfNotExisting(String name) {
        String nameKey = name.toLowerCase();
        htAccounts.putIfAbsent(nameKey, new Account(name));
    }

    private Account getAccount(String name) {
        String nameKey = name.toLowerCase();
        return htAccounts.get(nameKey);
    }

    private boolean isProbableHeaderRow(String[] transactionRow) {
        return TextHandler.removeExcessSpace(transactionRow[0]).toLowerCase().contains("date");
    }

    private boolean isConvertibleToTransaction(String[] transactionRow) {
        LOGGER.info("Checking validity of transaction " + Arrays.toString(transactionRow));
        if (transactionRow.length != 5)
            return false;
        try {
            new BigDecimal(transactionRow[4]);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void displayErrorForRow(String[] transactionRow, int index) {
        if (transactionRow.length != 5) {
            LOGGER.error("Wrong number of fields in row " + Arrays.toString(transactionRow) + " (should be 5)");
            System.out.println("Row " + index + ": expected 5 items in row, got " + transactionRow.length);
        }
        try {
            new BigDecimal(transactionRow[4]);
        }
        catch (NumberFormatException e) {
            LOGGER.error(Arrays.toString(transactionRow) + " - final value could not be converted to amount");
            System.out.println("Row " + index + ": couldn't convert \"" + transactionRow[4] + "\" to monetary amount");
        }
    }

    private static void warnErroneousRowsIgnored() {
        System.out.println();
        System.out.println(TextHandler.getBoldText("The affected rows will be ignored"));
        System.out.println("--------------------");
        System.out.println();
    }

    public void printAllAccountsAndBalances() {
        LOGGER.info("Attempting to print all account names and balances");
        for (String personKey: htAccounts.keySet()) {
            Account currentAccount = getAccount(personKey);
            System.out.println(currentAccount.getName() + ": " + currentAccount.getBalance());
        }
    }

    public void printTransactionsForPerson(String name) {
        LOGGER.info("Attempting to print account information and transactions for \"" + name + "\"");
        String nameKey = TextHandler.removeExcessSpace(name).toLowerCase();

        if (!htAccounts.containsKey(nameKey))
            System.out.println("No account found for \"" + name + "\"");
        else {
            Account thisAccount = getAccount(nameKey);
            System.out.println("Account transaction details for " + thisAccount.getName() + ":");
            for (Transaction transaction: thisAccount.getTransactions()) {
                transaction.printTransaction();
            }
        }
    }
}
