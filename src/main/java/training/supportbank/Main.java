package training.supportbank;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser("DodgyTransactions2015.csv");
        List<String[]> transactionTable = csvParser.getTable();
        if (transactionTable == null)
        {
            System.out.println("Error reading file! Please ensure file exists.");
            return;
        }
        AccountsRegister accountsRegister = new AccountsRegister(transactionTable);

        accountsRegister.printAllAccountsAndBalances();
        System.out.println();
        accountsRegister.printTransactionsForPerson("Sarah T");
    }
}
