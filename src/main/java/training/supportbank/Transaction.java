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
        this.date = TextHandler.removeExcessSpace(date);
        this.from = TextHandler.removeExcessSpace(from);
        this.to = TextHandler.removeExcessSpace(to);
        this.narrative = TextHandler.removeExcessSpace(narrative);
        this.amount = amount;
        LOGGER.info("Created a transaction between " + this.from + " and " + this.to);
    }

    public Transaction(String[] transactionRow) {
        this.date = TextHandler.removeExcessSpace(transactionRow[0]);
        this.from = TextHandler.removeExcessSpace(transactionRow[1]);
        this.to = TextHandler.removeExcessSpace(transactionRow[2]);
        this.narrative = TextHandler.removeExcessSpace(transactionRow[3]);
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
