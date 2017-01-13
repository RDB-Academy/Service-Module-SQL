package sqlParser;


import java.util.*;

public class SQLResultSet {
    private List<SQLResultColumn> columns;
    private String error;
    private String hint;

    public SQLResultSet() {
        columns = new ArrayList<>();
    }

    public List<SQLResultColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<SQLResultColumn> columns) {
        this.columns = columns;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
