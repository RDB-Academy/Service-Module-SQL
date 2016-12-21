package parser;

import models.submodels.ResultSet;

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

    public boolean isCorrect() {
        return isCorrect;
    }

    public ResultSet getAsResultSet() {
        ResultSet resultSet;

        resultSet = new ResultSet();

        resultSet.setHeader(header);
        resultSet.setDataSets(dataSets);
        resultSet.setHintMessage(hint);
        resultSet.setErrorMessage(error);

        return resultSet;
    }
}

