package training.supportbank;

import com.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVParser extends Parser {

    public CSVParser(String filename) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filename));
            setTable(reader.readAll());
            LOGGER.info("Parsed the file successfully!");
        }
        catch (IOException e) {
            LOGGER.error("Something was wrong with the file");
        }
    }

}
