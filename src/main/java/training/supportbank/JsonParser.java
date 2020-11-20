package training.supportbank;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser extends Parser {
    private static final Logger LOGGER = LogManager.getLogger();

    public JsonParser(String filename) {
        try {
            List<Transaction> newTable = new ArrayList<>();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(filename));
            Transaction[] transactions = gson.fromJson(reader, Transaction[].class);
            int i = 0;
            for (Transaction newTransaction : transactions) {
                try {
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
        } catch (IOException e) {
            LOGGER.error("File I/O error - file \"" + filename + "\" might not exist");
        }
    }
}
