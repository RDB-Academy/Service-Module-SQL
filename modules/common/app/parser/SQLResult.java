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

        resultSet.getColumns().forEach(sqlResultColumn -> {
            this.header.add(sqlResultColumn.getName());
            this.dataSets.add(sqlResultColumn.getData());
        });
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

