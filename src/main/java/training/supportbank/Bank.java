package training.supportbank;

import java.util.List;
import java.util.HashMap;

public class Bank {
    private String sourceFile = null;
    private List<Transaction> transactions = null;
    private final HashMap<String, Account> accountsMap = new HashMap<>();

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void updateAccounts() {
        accountsMap.clear();

        for (Transaction transaction: transactions) {
            String sender = transaction.getFrom();
            String recipient = transaction.getTo();

            createAccountIfNotExisting(sender);
            createAccountIfNotExisting(recipient);

            getAccount(sender).addOutgoingTransaction(transaction);
            getAccount(recipient).addIncomingTransaction(transaction);
        }
    }

    private void createAccountIfNotExisting(String name) {
        String nameKey = name.toLowerCase();
        accountsMap.putIfAbsent(nameKey, new Account(name));
    }

    private Account getAccount(String name) {
        String nameKey = name.toLowerCase();
        return accountsMap.get(nameKey);
    }

    public void printAllAccountsAndBalances() {
        DialogueHandler.logInfo("Attempting to print all accounts and balances");
        System.out.println("All accounts currently loaded:");
        for (String personKey: accountsMap.keySet()) {
            Account currentAccount = getAccount(personKey);
            System.out.printf("%11s | %s\n",
                    currentAccount.getName(), TextHandler.getGeneralMonetary(currentAccount.getBalance()));
        }
    }

    public void printTransactionsForPerson(String name) {
        DialogueHandler.logInfo("Attempting to print account information and transactions for \"" + name + "\"");
        String nameKey = TextHandler.removeExcessSpace(name).toLowerCase();

        if (!accountsMap.containsKey(nameKey))
            DialogueHandler.outputError("No account found for \"" + name + "\".");
        else {
            Account thisAccount = getAccount(nameKey);
            System.out.println("Account transaction details for " + thisAccount.getName() + ":");
            for (Transaction transaction: thisAccount.getTransactions()) {
                transaction.printTransaction();
            }
        }
    }
}
