package training.supportbank;

import com.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;

public class CSVParser extends Parser {
    private static final Logger LOGGER = LogManager.getLogger();

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
