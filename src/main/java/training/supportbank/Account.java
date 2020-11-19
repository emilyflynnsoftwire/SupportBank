package training.supportbank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private BigDecimal balance = new BigDecimal("0");

    public Account(String name) {
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
