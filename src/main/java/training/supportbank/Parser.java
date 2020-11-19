package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class Parser {
    private List<String[]> table = null;

    public List<String[]> getTable() {
        return table;
    }

    public void setTable(List<String[]> table) {
        this.table = table;
    }

}
