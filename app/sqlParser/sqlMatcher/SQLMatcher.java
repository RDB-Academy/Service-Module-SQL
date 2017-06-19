package sqlParser.sqlMatcher;

import sqlParser.SQLResult;
import sqlParser.SQLResultColumn;
import sqlParser.SQLResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SQLMatcher {

    public static SQLResult match(SQLResultSet userResultSet, SQLResultSet refResultSet) {
        boolean     isCorrect       = false;
        String      errorMessage;
        String      hintMessage     = null;

        if(userResultSet.getErrorMessage() != null) {
            errorMessage = userResultSet.getErrorMessage();

            return newSQLResult(
                    userResultSet,
                    false,
                    errorMessage,
                    null
            );
        }

        if(refResultSet.getErrorMessage() != null) {
            errorMessage = refResultSet.getErrorMessage();

            return newSQLResult(
                    userResultSet,
                    false,
                    errorMessage,
                    null
            );
        }

        for (SQLResultColumn refResultColumn : refResultSet.getColumns()) {

            boolean columnValid = false;
            boolean columnExist = false;
            for (SQLResultColumn userResultColumn : userResultSet.getColumns()) {
                if(userResultColumn.getType().equals(refResultColumn.getType())){
                    columnExist = true;
                    if(userResultColumn.getData().containsAll(refResultColumn.getData()) && refResultColumn.getData().containsAll(userResultColumn.getData())){
                        columnValid = true;
                        break;
                    }
                }
            }
            if(!columnValid){
                isCorrect = false;
                if(!columnExist){
                    hintMessage = "Missing Column: " + refResultColumn.getAlias() + "("+ refResultColumn.getType()+ ")";
                    break;
                }
                hintMessage = "Query is not correct yet.";
                break;
            }else{
                isCorrect = true;
            }
        }

        return newSQLResult(
                userResultSet,
                isCorrect,
                null,
                hintMessage
        );
    }

    private static SQLResult newSQLResult(
            SQLResultSet    userResultSet,
            boolean         isCorrect,
            String          errorMessage,
            String          hintMessage) {

        List<String> headerList;
        List<List<String>> dataSet  = new ArrayList<>();

        headerList = userResultSet.getColumns()
                .stream()
                .map(SQLResultColumn::getAlias)
                .collect(Collectors.toList());


        for(int i = 0; i < userResultSet.getColumns().size(); i++) {
            SQLResultColumn column = userResultSet.getColumns().get(i);

            List<String> columnData = column.getData();

            for (int j = 0; j < columnData.size(); j++) {
                String data = columnData.get(j);
                List<String> row;

                if(j == dataSet.size()) {
                    row = new ArrayList<>();
                    dataSet.add(row);
                } else {
                    row = dataSet.get(j);
                }
                row.add(data);
                // add data to dataSetRow
            }
        }

        return new SQLResult(
                headerList,
                dataSet,
                isCorrect,
                errorMessage,
                hintMessage
        );
    }
}
