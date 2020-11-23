package training.supportbank;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class Account {
    private final String name;
    private final List<Transaction> transactions = new ArrayList<>();
    private BigDecimal balance = new BigDecimal(0);

    public Account(String name) {
        DialogueHandler.logInfo("Creating an account for " + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addIncomingTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateBalance(transaction.getAmount());
    }

    public void addOutgoingTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateBalance(transaction.getAmount().negate());
    }

    public void updateBalance(BigDecimal amountToAdd) {
        balance = balance.add(amountToAdd);
    }
}
