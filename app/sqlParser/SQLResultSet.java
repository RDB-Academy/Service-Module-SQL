package sqlParser;


import java.util.*;

public class SQLResultSet {

    private List<SQLResultColumn> columns;

    private String errorMessage;

    public SQLResultSet() {
        columns = new ArrayList<>();
    }

    public List<SQLResultColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<SQLResultColumn> columns) {
        this.columns = columns;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
