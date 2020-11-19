package training.supportbank;

import java.math.BigDecimal;

public class Transaction {
    private String date;
    private String from;
    private String to;
    private String narrative;
    private BigDecimal amount;

    public Transaction(String date, String from, String to, String narrative, BigDecimal amount) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.narrative = narrative;
        this.amount = amount;
    }

    public Transaction(String[] transactionRow) {
        this.date = transactionRow[0];
        this.from = transactionRow[1];
        this.to = transactionRow[2];
        this.narrative = transactionRow[3];
        this.amount = new BigDecimal(transactionRow[4]);
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getNarrative() {
        return narrative;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void printTransaction() {
        System.out.println(date + " - from " + from + " to " + to + " - £" + amount + " (" + narrative + ")");
    }
}
