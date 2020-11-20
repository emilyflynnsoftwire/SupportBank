package training.supportbank;

import java.util.List;

public abstract class Parser {
    private List<Transaction> table = null;

    public List<Transaction> getTable() {
        return table;
    }

    public void setTable(List<Transaction> table) {
        this.table = table;
    }

}
