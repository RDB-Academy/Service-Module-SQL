package parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fabiomazzone
 */
public class SQLResult {
    private SQLResultSet resultSet;
    private boolean      isCorrect;

    public class SQLResultSet {
        List<String> header;
        List<List<String>> dataSets;

        public SQLResultSet(List<String> headerList, List<List<String>> dataSet) {
            this.header = headerList;
            this.dataSets = dataSet;
        }

        public List<String> getHeader() {
            return header;
        }

        public List<List<String>> getDatasets() {
            return dataSets;
        }
    }

    SQLResult(List<List<String>> resultSet, boolean isCorrect) {
        this.isCorrect = isCorrect;
        List<List<String>> dataSet = new ArrayList<>(resultSet);
        dataSet.remove(0);

        this.resultSet = new SQLResultSet(resultSet.get(0), dataSet);
    }

    public SQLResultSet getResultSet() {
        return resultSet;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
