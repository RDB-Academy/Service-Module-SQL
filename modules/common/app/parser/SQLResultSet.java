package parser;

import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class SQLResultSet {
    private List<List<String>> resultSet;
    private String error;

    public SQLResultSet() {
        resultSet = new ArrayList<>();
    }

    public List<List<String>> getResultSet() {
        return resultSet;
    }

    public void setResultSet(List<List<String>> resultSet) {
        this.resultSet = resultSet;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    //
    boolean isSubsetOf(SQLResultSet userResultSet) {
        List<String> refHeader;
        List<String> userHeader;

        Logger.debug("isSubsetOf");

        if(userResultSet.getError() != null) {
            return false;
        }

        refHeader = new ArrayList<>(this.getResultSet().get(0));
        userHeader = new ArrayList<>(userResultSet.getResultSet().get(0));

        if (!userHeader.containsAll(refHeader)) {
            Logger.warn("UserHeader doesn't contain all refHeader");
            refHeader.removeAll(userHeader);
            for(String header : refHeader) {
                System.out.println(header);
            }
            return false;
        }
        Logger.info("UserHeader contains all refHeader");

        if(this.getResultSet().size() != userResultSet.getResultSet().size()) {
            Logger.warn("ResultsSets Size is not equal");
            return false;
        }
        Logger.warn("ResultsSets Size is equal");

        return false;
    }
}
