package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final LocalDate EXCEL_START_DATE = LocalDate.of( 1899 , 12 , 30 );
    private String date;
    private  String fromAccount;
    private  String toAccount;
    private  String narrative;
    private  BigDecimal amount;
    private LocalDate dateObject;

    public Transaction(String date, String from, String to, String narrative, BigDecimal amount) {
        this.date = date;
        this.fromAccount = from;
        this.toAccount = to;
        this.narrative = narrative;
        this.amount = amount;
        LOGGER.info("Created a transaction between " + this.fromAccount + " and " + this.toAccount);
    }

    public Transaction(String[] transactionRow) {
        date = transactionRow[1];
        fromAccount = transactionRow[1];
        toAccount = transactionRow[2];
        narrative = transactionRow[3];
        amount = new BigDecimal(transactionRow[4]);
        LOGGER.info("Created a transaction between " + this.fromAccount + " and " + this.toAccount);
    }

    public void setup(){
        cleanStrings();
        initialiseDateObject();
    }

    public void cleanStrings(){
        setFromAccount(TextHandler.removeExcessSpace(fromAccount));
        setToAccount(TextHandler.removeExcessSpace(toAccount));
        setNarrative(TextHandler.removeExcessSpace(narrative));
    }

    public void initialiseDateObject(){
        LocalDate tranDate = null;
        if(date.indexOf('/')!=-1){//csv
            String[] splitDate = date.split("/");
            int day = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]);
            int year = Integer.parseInt(splitDate[2]);
            tranDate = LocalDate.of(year, month, day);
        } else if(date.indexOf('-')!=-1) {//json
            String[] splitDate = date.split("-");
            int year = Integer.parseInt(splitDate[0]);
            int month = Integer.parseInt(splitDate[1]);
            int day = Integer.parseInt(splitDate[2]);
            tranDate = LocalDate.of(year, month, day);
        } else if(date.matches("[0-9]+") && date.length() == 5) {//xml (excel format)
            tranDate = EXCEL_START_DATE.plusDays(Long.parseLong(date));
        } else {
            LOGGER.error("Date was in an unknown format");
            return;
        }
        setDateObject(tranDate);
    }

    public void setDateObject(LocalDate dateObject) {
        this.dateObject = dateObject;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public void setAmount(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void printTransaction() {
        System.out.println(dateObject + " - from " + fromAccount + " to " + toAccount + " - £" + amount + " (" + narrative + ")");
    }
}
