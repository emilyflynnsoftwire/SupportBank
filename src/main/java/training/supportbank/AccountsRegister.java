package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;

public class AccountsRegister {
    private static final Logger LOGGER = LogManager.getLogger();

    private HashMap<String, Account> htAccounts = new HashMap<String, Account>();
    private HashMap<String, String> htNameAsFirstEntered = new HashMap<String, String>();

    public AccountsRegister(List<String[]> transactionsTable) {
        LOGGER.info("Attempting to create a register of accounts");
        for (String[] transactionRow: transactionsTable) {
            if (isConvertibleToTransaction(transactionRow)) {
                LOGGER.info("Adding accounts for transaction participants if not already existing");
                String sender = TextHandler.simplifyStringAndMakeLowercase(transactionRow[1]);
                String recipient = TextHandler.simplifyStringAndMakeLowercase(transactionRow[2]);

                htAccounts.putIfAbsent(sender, new Account(sender));
                htAccounts.putIfAbsent(recipient, new Account(recipient));
                htNameAsFirstEntered.putIfAbsent(sender, TextHandler.simplifyString(transactionRow[1]));
                htNameAsFirstEntered.putIfAbsent(recipient, TextHandler.simplifyString(transactionRow[2]));

                Transaction currentTransaction = new Transaction(transactionRow);
                htAccounts.get(sender).addOutgoingTransaction(currentTransaction);
                htAccounts.get(recipient).addIncomingTransaction(currentTransaction);
            }
            else {
                LOGGER.error("A transaction could not be created for row " + Arrays.toString(transactionRow));
            }
        }
    }

    private boolean isConvertibleToTransaction(String[] transactionRow) {
        LOGGER.error("Checking validity of transaction " + Arrays.toString(transactionRow));
        if (transactionRow.length != 5) {
            LOGGER.error("Wrong number of fields in row " + Arrays.toString(transactionRow) + " (should be 5)");
            return false;
        }
        try {
            new BigDecimal(transactionRow[4]);
        }
        catch (NumberFormatException e) {
            LOGGER.error(Arrays.toString(transactionRow) + " - final value could not be converted to amount");
            return false;
        }
        return true;
    }

    public void printAllAccountsAndBalances() {
        LOGGER.info("Attempting to print all account names and balances");
        for (String person: htAccounts.keySet()) {
            String nameAsFirstEntered = htNameAsFirstEntered.get(person);
            Account currentAccount = htAccounts.get(person);
            System.out.println(nameAsFirstEntered + ": " + currentAccount.getBalance());
        }
    }

    public void printTransactionsForPerson(String name) {
        LOGGER.info("Attempting to print account information and transactions for \"" + name + "\"");
        String simplifiedName = TextHandler.simplifyStringAndMakeLowercase(name);

        if (!htAccounts.containsKey(simplifiedName))
            System.out.println("No account found for \"" + name + "\"");
        else {
            Account thisAccount = htAccounts.get(simplifiedName);
            System.out.println("Account transaction details for " + htNameAsFirstEntered.get(simplifiedName) + ":");
            for (Transaction transaction: thisAccount.getTransactions()) {
                transaction.printTransaction();
            }
        }
    }
}
