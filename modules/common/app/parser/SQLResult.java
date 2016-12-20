package parser;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fabiomazzone
 */
public class SQLResult {
    private List<String>    header;
    private List<List<String>> dataSets;

    private boolean         isCorrect;
    private String    error;
    private String    hint;

    SQLResult(SQLResultSet resultSet, boolean isCorrect) {
        this.isCorrect = isCorrect;
        this.header = new ArrayList<>();
        this.dataSets = new ArrayList<>();

        if(resultSet.getError() != null) {
            this.isCorrect = false;
            this.error = resultSet.getError();
            return;
        }
        if(resultSet.getHint() != null) {
            this.isCorrect = false;
            this.hint = resultSet.getHint();
        }

        for(String headerName : resultSet.getResultSet().get(0)) {
            this.header.add(headerName);
        }

        for(int i = 1; i < resultSet.getResultSet().size(); i++) {
            List<String> row = resultSet.getResultSet().get(i);
            this.dataSets.add(row);
        }
    }

    public List<String> getHeader() {
        return header;
    }

    @JsonGetter("datasets")
    public List<List<String>> getDataSets() {
        return dataSets;
    }

    @JsonGetter("errorMessage")
    public String getError() {
        return error;
    }

    @JsonGetter("hintMessage")
    public String getHint() {
        return hint;
    }
}

