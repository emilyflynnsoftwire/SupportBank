package training.supportbank;

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
