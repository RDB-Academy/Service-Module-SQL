package parser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLResultSet {
    private List<List<String>> resultSet;
    private SQLException error;

    public SQLResultSet() {
        resultSet = new ArrayList<>();
    }

    public List<List<String>> getResultSet() {
        return resultSet;
    }

    public void setResultSet(List<List<String>> resultSet) {
        this.resultSet = resultSet;
    }

    public SQLException getError() {
        return error;
    }

    public void setError(SQLException error) {
        this.error = error;
    }
}
