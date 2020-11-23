package training.supportbank;

public class RawTransaction {
    private String date = null;
    private String fromAccount = null;
    private String toAccount = null;
    private String narrative = null;
    private String amount = null;

    public RawTransaction(String[] transactionRow) {
        if (transactionRow.length > 0)
            date = TextHandler.removeExcessSpace(transactionRow[0]);
        if (transactionRow.length > 1)
            fromAccount = TextHandler.removeExcessSpace(transactionRow[1]);
        if (transactionRow.length > 2)
            toAccount = TextHandler.removeExcessSpace(transactionRow[2]);
        if (transactionRow.length > 3)
            narrative = TextHandler.removeExcessSpace(transactionRow[3]);
        if (transactionRow.length > 4)
            amount = TextHandler.removeExcessSpace(transactionRow[4]);
    }

    public RawTransaction(String date, String fromAccount, String toAccount, String narrative, String amount) {
        this.date = TextHandler.removeExcessSpace(date);
        this.fromAccount = TextHandler.removeExcessSpace(fromAccount);
        this.toAccount = TextHandler.removeExcessSpace(toAccount);
        this.narrative = TextHandler.removeExcessSpace(narrative);
        this.amount = TextHandler.removeExcessSpace(amount);
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return fromAccount;
    }

    public String getTo() {
        return toAccount;
    }

    public String getNarrative() {
        return narrative;
    }

    public String getAmount() {
        return amount;
    }
}
