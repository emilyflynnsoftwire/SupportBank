package training.supportbank;

import com.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser extends Parser {
    private static final Logger LOGGER = LogManager.getLogger();

    public CSVParser(String filename) {
        try {
            List<Transaction> newTable = new ArrayList<>();
            CSVReader reader = new CSVReader(new FileReader(filename));
            int i = 0;
            for(String[] csvLine: reader) {
                try {
                    Transaction newTransaction = new Transaction(csvLine);
                    newTransaction.setup();
                    newTable.add(newTransaction);
                } catch (Exception e){
                    if(i!=0){//probably title line
                        LOGGER.error("Error with line " + i);
                    }
                }
                i++;
            }
            setTable(newTable);
            LOGGER.info("Parsed the file successfully!");
        }
        catch (IOException e) {
            LOGGER.error("File I/O error - file \"" + filename + "\" might not exist");
        }
    }
}
