package training.supportbank;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVParser extends Parser {

    public CSVParser(String filename) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filename));
            setTable(reader.readAll());
        }
        catch (IOException e) {
            
        }
    }

}
