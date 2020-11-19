package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.info("New session started");

//        Parser csvParser = new CSVParser("Transactions2014.csv");
//        List<String[]> transactionTable = csvParser.getTable();

        Parser jsonParser = new JsonParser("Transactions2013.json");
        List<String[]> transactionTable = jsonParser.getTable();
        if (transactionTable == null)
        {
            System.out.println("Error reading file! Please ensure file exists.");
            LOGGER.error("Error reading file");
            return;
        }
        AccountsRegister accountsRegister = new AccountsRegister(transactionTable);

        if (args.length != 0) {
            if (args[1].toLowerCase().equals("all")) {
                accountsRegister.printAllAccountsAndBalances();
            } else {
                String personToList = args[1] + " " + args[2];
                accountsRegister.printTransactionsForPerson(personToList);
            }
        }
    }
}
