package parser;

/**
 * @author fabiomazzone
 */
public class SQLResult {
    private String[]    resultSet;
    private boolean     isCorrect;

    SQLResult(String[] resultSet, boolean isCorrect) {
        this.resultSet = resultSet;
        this.isCorrect = isCorrect;
    }

    public String[] getResultSet() {
        return resultSet;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
