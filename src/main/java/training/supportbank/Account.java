package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private BigDecimal balance = new BigDecimal("0");
    private static final Logger LOGGER = LogManager.getLogger();


    public Account(String name) {
        LOGGER.info("Creating an account for " + name);
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
