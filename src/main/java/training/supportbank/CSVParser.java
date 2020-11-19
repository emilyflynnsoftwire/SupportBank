package training.supportbank;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVParser {
    private List<String[]> table;

    public CSVParser(String filename) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filename));
            table = reader.readAll();
        }
        catch (IOException e) {
            table = null;
        }
    }

    public List<String[]> getTable() {
        return table;
    }
}
