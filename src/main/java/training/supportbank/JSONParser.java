package training.supportbank;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.JsonParseException;

import java.io.FileReader;
import java.io.IOException;

public class JSONParser extends Parser {
    RawTransaction[] readingData = null;

    public JSONParser(String filename) {
        setFilename(filename);
    }

    @Override
    public boolean checkIntermediaryDataAfterReading() {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(getFilename()));
            this.readingData = gson.fromJson(reader, RawTransaction[].class);
            return true;
        } catch (IOException e) {
            DialogueHandler.outputIOExceptionMessage(getFilename());
        } catch (JsonParseException e) {
            DialogueHandler.outputJSONParseExceptionMessage(getFilename());
        }
        return false;
    }

    @Override
    public boolean checkExtractedRawTransactionData() {
        setRawTransactionData(readingData);
        return true;
    }
}
