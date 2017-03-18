package sqlParser;

import models.submodels.ResultSet;
import java.util.List;

/**
 * @author fabiomazzone
 */
public class SQLResult {
    private List<String>    header;
    private List<List<String>> dataSets;

    private boolean   isCorrect;
    private String    error;
    private String    hint;

    public SQLResult(
            List<String>        header,
            List<List<String>>  dataSets,
            boolean             isCorrect,
            String              error,
            String              hint        ) {

        this.header = header;
        this.dataSets = dataSets;
        this.isCorrect = isCorrect;
        this.error = error;
        this.hint = hint;
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

