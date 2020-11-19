package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class Transaction {
    private static final Logger LOGGER = LogManager.getLogger();

    private final String date;
    private final String from;
    private final String to;
    private final String narrative;
    private final BigDecimal amount;

    public Transaction(String date, String from, String to, String narrative, BigDecimal amount) {
        this.date = TextHandler.simplifyString(date);
        this.from = TextHandler.simplifyString(from);
        this.to = TextHandler.simplifyString(to);
        this.narrative = TextHandler.simplifyString(narrative);
        this.amount = amount;
        LOGGER.info("Created a transaction between " + this.from + " and " + this.to);
    }

    public Transaction(String[] transactionRow) {
        this.date = TextHandler.simplifyString(transactionRow[0]);
        this.from = TextHandler.simplifyString(transactionRow[1]);
        this.to = TextHandler.simplifyString(transactionRow[2]);
        this.narrative = TextHandler.simplifyString(transactionRow[3]);
        this.amount = new BigDecimal(transactionRow[4]);
        LOGGER.info("Created a transaction between " + this.from + " and " + this.to);

    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void printTransaction() {
        System.out.println(date + " - from " + from + " to " + to + " - £" + amount + " (" + narrative + ")");
    }
}
