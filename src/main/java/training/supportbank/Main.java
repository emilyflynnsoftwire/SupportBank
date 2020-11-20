package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.info("New session started!");

        CSVParser csvParser = new CSVParser("DodgyTransactions2015.csv");
        List<String[]> transactionTable = csvParser.getTable();
        if (transactionTable == null)
        {
            LOGGER.error("Exiting due to file I/O error.");
            System.out.println("Error reading file! Please ensure file exists.");
            return;
        }
        AccountsRegister accountsRegister = new AccountsRegister(transactionTable);
        ArgumentHandler.run(accountsRegister);
    }
}
