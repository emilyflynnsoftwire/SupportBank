package training.supportbank;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class AccountsRegister {
    private HashMap<String, Account> htAccounts = new HashMap<String, Account>();

    public AccountsRegister(List<String[]> transactionsTable) {
        for (String[] transactionRow: transactionsTable) {
            if (isConvertibleToTransaction(transactionRow)) {
                htAccounts.putIfAbsent(transactionRow[1], new Account(transactionRow[1]));
                htAccounts.putIfAbsent(transactionRow[2], new Account(transactionRow[2]));

                Transaction currentTransaction = new Transaction(transactionRow);
                htAccounts.get(transactionRow[1]).addOutgoingTransaction(currentTransaction);
                htAccounts.get(transactionRow[2]).addIncomingTransaction(currentTransaction);
            }
        }
    }

    private boolean isConvertibleToTransaction(String[] transactionRow) {
        if (transactionRow.length != 5)
            return false;
        try {
            BigDecimal amountAsDecimal = new BigDecimal(transactionRow[4]);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void printAllAccountsAndBalances() {
        for (String person: htAccounts.keySet()) {
            Account currentAccount = htAccounts.get(person);
            System.out.println(person + ": " + currentAccount.getBalance());
        }
    }

    public void printTransactionsForPerson(String name) {
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
