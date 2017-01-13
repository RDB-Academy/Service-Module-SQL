package sqlParser.sqlMatcher;

import sqlParser.SQLResult;
import sqlParser.SQLResultColumn;
import sqlParser.SQLResultSet;

/**
 * Created by fabiomazzone on 05.01.17.
 */
public class SQLMatcher {

    public static SQLResult match(SQLResultSet userResultSet, SQLResultSet refResultSet) {

        if(userResultSet.getError() != null) {
           return new SQLResult(userResultSet, false);
        }
        if(refResultSet.getError() != null) {
            userResultSet.setError(refResultSet.getError());
            return new SQLResult(userResultSet, false);
        }

        boolean result = true;

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
                result = false;
                if(!columnExist){
                    userResultSet.setHint("Missing Column: " + refResultColumn.getName() + "("+ refResultColumn.getType()+ ")");
                    break;
                }
                userResultSet.setHint(refResultColumn.getName() + " has not been filled adequately.");
                break;
            }
        }
        return new SQLResult(userResultSet, result);
    }
}
