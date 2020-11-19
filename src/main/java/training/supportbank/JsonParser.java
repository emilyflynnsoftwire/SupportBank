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
            List<String[]> newTable = new ArrayList<>();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(filename));
            JsonLine[] jsonLines = gson.fromJson(reader, JsonLine[].class);
            for (int i = 0; i < jsonLines.length; i++) {
                JsonLine line = jsonLines[i];
                String[] tableLine = {line.getDate(),
                        line.getFromAccount(),
                        line.getToAccount(),
                        line.getNarrative(),
                        line.getAmount()};
                newTable.add(tableLine);
            }
            setTable(newTable);
            LOGGER.info("Parsed the file successfully!");
        } catch (IOException e) {
            LOGGER.error("Something was wrong with the file");
        }
    }
}
