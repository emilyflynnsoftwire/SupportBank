package training.supportbank;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private static final LocalDate EXCEL_START_DATE = LocalDate.of(1899, 12, 30);

    private final LocalDate dateObject;
    private final String from;
    private final String to;
    private final String narrative;
    private final BigDecimal amount;

    public Transaction(RawTransaction rawTransaction) {
        dateObject = getDateObjectFromString(rawTransaction.getDate());
        from = rawTransaction.getFrom();
        to = rawTransaction.getTo();
        narrative = rawTransaction.getNarrative();
        amount = new BigDecimal(rawTransaction.getAmount());
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public static LocalDate getDateObjectFromString(String date) {
        LocalDate dateObject = null;
        if (date.indexOf('/') != -1) {
            String[] splitDate = date.split("/");
            int day = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]);
            int year = Integer.parseInt(splitDate[2]);
            dateObject = LocalDate.of(year, month, day);
        } else if (date.indexOf('-') != -1) {
            String[] splitDate = date.split("-");
            int year = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]);
            int day = Integer.parseInt(splitDate[2]);
            dateObject = LocalDate.of(year, month, day);
        } else if (date.matches("[0-9]+") && date.length() == 5) {
            dateObject = EXCEL_START_DATE.plusDays(Long.parseLong(date));
        } else {
            DialogueHandler.logInfo("Date was in an unknown format");
        }
        return dateObject;
    }

    public void printTransaction() {
        System.out.println(dateObject + " - from " + from + " to " + to + " - £" + amount + " (" + narrative + ")");
    }
}
