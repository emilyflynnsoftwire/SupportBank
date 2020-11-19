package training.supportbank;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser("Transactions2014.csv");
        List<String[]> transactionTable = csvParser.getTable();
        if (transactionTable == null)
        {
            System.out.println("Error reading file! Please ensure file exists.");
            return;
        }
        AccountsRegister accountsRegister = new AccountsRegister(transactionTable);

        if(args.length!=0) {
            if (args[1].toLowerCase().equals("all")) {
                accountsRegister.printAllAccountsAndBalances();
            } else{
                String personToList = args[1] + " " + args[2];
                accountsRegister.printTransactionsForPerson(personToList);
            }
        }
    }
}
