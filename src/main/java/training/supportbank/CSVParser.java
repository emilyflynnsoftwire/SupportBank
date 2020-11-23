package training.supportbank;

import com.opencsv.CSVReader;

import java.util.List;
import java.io.FileReader;
import java.io.IOException;

public class CSVParser extends Parser {
    List<String[]> readingData = null;

    public CSVParser(String filename) {
        setFilename(filename);
    }

    @Override
    public boolean checkIntermediaryDataAfterReading() {
        try {
            CSVReader reader = new CSVReader(new FileReader(getFilename()));
            this.readingData = reader.readAll();
            return true;
        }
        catch (IOException e) {
            DialogueHandler.outputIOExceptionMessage(getFilename());
        }
        return false;
    }

    @Override
    public boolean checkExtractedRawTransactionData() {
        setRawTransactionData(readingData.stream().map(RawTransaction::new).toArray(RawTransaction[]::new));
        return true;
    }
}
