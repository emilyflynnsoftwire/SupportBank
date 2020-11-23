package training.supportbank;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class Parser {
    private String filename;
    private RawTransaction[] rawTransactionData = null;
    private List<Transaction> transactionTable = null;
    private List<String> errors = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();

    private static final BigDecimal LARGE_TRANSACTION_THRESHOLD = new BigDecimal(1000000);

    public List<Transaction> getParsedTransactionTableFromFile() {
        if (checkFileExists() &&
            checkIntermediaryDataAfterReading() &&
            checkExtractedRawTransactionData() &&
            checkInterpretedTransactionDataNotEmpty())
            return getTransactionTable();
        else
            return null;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setRawTransactionData(RawTransaction[] rawTransactionData) {
        this.rawTransactionData = rawTransactionData;
    }

    public List<Transaction> getTransactionTable() {
        return transactionTable;
    }

    public void setTransactionTable(List<Transaction> transactionTable) {
        this.transactionTable = transactionTable;
    }

    public boolean checkInterpretedTransactionDataNotEmpty() {
        List<Transaction> newTransactionTable = new ArrayList<>();
        int headerRowOffset = 0;

        while (headerRowOffset < rawTransactionData.length && isProbableHeaderRow(rawTransactionData[headerRowOffset]))
            headerRowOffset++;
        for (int index = headerRowOffset; index < rawTransactionData.length; index++) {
            RawTransaction rawTransaction = rawTransactionData[index];
            if (checkIsValidTransactionAndAddNotifications(rawTransaction, index + 1))
                newTransactionTable.add(new Transaction(rawTransaction));
        }
        DialogueHandler.outputTransactionNotifications(errors, warnings);
        if (newTransactionTable.isEmpty())
            DialogueHandler.outputEmptyDataMessage(filename);
        else
            setTransactionTable(newTransactionTable);
        return newTransactionTable.size() > 0;
    }

    private boolean isProbableHeaderRow(RawTransaction rawTransaction) {
        return rawTransaction.getDate().toLowerCase().contains("date");
    }

    private boolean checkIsValidTransactionAndAddNotifications(RawTransaction rawTransaction, int transactionNum) {
        List<String> currentErrors = new ArrayList<>();
        List<String> currentWarnings = new ArrayList<>();

        if (rawTransaction.getDate() == null || rawTransaction.getDate().isEmpty())
            currentErrors.add("date field is empty");
        else if (Transaction.getDateObjectFromString(rawTransaction.getDate()) == null)
            currentErrors.add("value in date field is not in a valid date format");
        if (rawTransaction.getFrom() == null || rawTransaction.getFrom().isEmpty())
            currentErrors.add("sender (\"from\") field is empty");
        if (rawTransaction.getTo() == null || rawTransaction.getTo().isEmpty())
            currentErrors.add("recipient (\"to\") field is empty");
        if (rawTransaction.getNarrative() == null || rawTransaction.getNarrative().isEmpty())
            currentErrors.add("narrative field is empty");
        if (rawTransaction.getAmount() == null || rawTransaction.getAmount().isEmpty())
            currentErrors.add("amount field is empty");
        else {
            try {
                BigDecimal amountAsDecimal = new BigDecimal(rawTransaction.getAmount());
                if (amountAsDecimal.multiply(new BigDecimal(100)).stripTrailingZeros().scale() > 0)
                    currentWarnings.add("value in amount field has more than 2 decimal places and will be rounded");
                if (amountAsDecimal.compareTo(LARGE_TRANSACTION_THRESHOLD) >= 0)
                    currentWarnings.add("value in amount field is very large and may have been mistyped");
            }
            catch (NumberFormatException e) {
                currentErrors.add("value in amount field is not in a valid numerical format");
            }
        }

        if (currentErrors.size() > 0)
            errors.add("Transaction " + transactionNum + " in file: " + String.join(", ", currentErrors));
        if (currentWarnings.size() > 0)
            warnings.add("Transaction " + transactionNum + " in file: " + String.join(", ", currentWarnings));
        return currentErrors.isEmpty();
    }

    public boolean checkFileExists() {
        File file = new File(filename);
        boolean fileExists = file.exists();
        if (!fileExists)
            DialogueHandler.outputError("Could not open \"" + filename + "\": file doesn't appear to exist.");
        return fileExists;
    }

    public abstract boolean checkIntermediaryDataAfterReading();

    public abstract boolean checkExtractedRawTransactionData();
}
