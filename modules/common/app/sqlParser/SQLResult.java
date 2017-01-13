package sqlParser;

import models.submodels.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fabiomazzone
 */
public class SQLResult {
    private List<String>    header;
    private List<List<String>> dataSets;

    private boolean         isCorrect;
    private String    error;
    private String    hint;

    public SQLResult(SQLResultSet resultSet, boolean isCorrect) {
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

        this.header = resultSet.getColumns().stream().map(SQLResultColumn::getAlias).collect(Collectors.toList());

        for(int i = 0; i < resultSet.getColumns().size(); i++) {
            SQLResultColumn column = resultSet.getColumns().get(i);

            List<String> columnData = column.getData();

            for (int j = 0; j < columnData.size(); j++) {
                String data = columnData.get(j);
                List<String> row;

                if(j == this.dataSets.size()) {
                    row = new ArrayList<>();
                    this.dataSets.add(row);
                } else {
                    row = this.dataSets.get(j);
                }
                row.add(data);
                // add data to dataSetRow
            }
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

