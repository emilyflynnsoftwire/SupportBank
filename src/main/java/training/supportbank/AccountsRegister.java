package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AccountsRegister {
    private static final Logger LOGGER = LogManager.getLogger();

    private HashMap<String, Account> htAccounts = new HashMap<String, Account>();

    public AccountsRegister(List<String[]> transactionsTable) {
        LOGGER.info("Attempting to create a register of accounts");
        for (String[] transactionRow: transactionsTable) {
            if (isConvertibleToTransaction(transactionRow)) {
                LOGGER.info("Adding accounts for transaction participants if not already existing");
                htAccounts.putIfAbsent(transactionRow[1], new Account(transactionRow[1]));
                htAccounts.putIfAbsent(transactionRow[2], new Account(transactionRow[2]));

                Transaction currentTransaction = new Transaction(transactionRow);
                htAccounts.get(transactionRow[1]).addOutgoingTransaction(currentTransaction);
                htAccounts.get(transactionRow[2]).addIncomingTransaction(currentTransaction);
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
            BigDecimal amountAsDecimal = new BigDecimal(transactionRow[4]);
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
            Account currentAccount = htAccounts.get(person);
            System.out.println(person + ": " + currentAccount.getBalance());
        }
    }

    public void printTransactionsForPerson(String name) {
        LOGGER.info("Attempting to print account information and transactions for \"" + name + "\"");
        if (!htAccounts.containsKey(name))
            System.out.println("No account found for \"" + name + "\"");
        else {
            Account thisAccount = htAccounts.get(name);
            System.out.println("Account transaction details for " + name + ":");
            for (Transaction transaction: thisAccount.getTransactions()) {
                transaction.printTransaction();
            }
        }
    }
}
