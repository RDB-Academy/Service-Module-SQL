package parser;

/**
 * Created by fabiomazzone on 05.01.17.
 */
public class SQLResultMatcher {
    public static SQLResult match(SQLResultSet userResultSet, SQLResultSet refResultSet) {


        return new SQLResult(userResultSet, false);
    }
}
