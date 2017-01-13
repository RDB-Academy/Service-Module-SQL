package sqlParser;

import models.TaskTrial;
import models.TaskTrialLog;
import sqlParser.sqlMatcher.SQLMatcher;
import play.Logger;

import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class SQLParser {
    private final TaskTrial taskTrial;
    private final Connection connection;

    SQLParser(
            TaskTrial taskTrial,
            Connection connection) {

        this.taskTrial = taskTrial;
        this.connection = connection;
    }

    /**
     *
     * @param taskTrial
     * @return
     */
    public SQLResult submit(TaskTrial taskTrial, TaskTrialLog taskTrialLog) {
        SQLResult       sqlResult;
        SQLResultSet    userResultSet;
        SQLResultSet    refResultSet;

        userResultSet   = executeStatement(taskTrialLog.getStatement());
        refResultSet    = executeStatement(this.taskTrial.getTask().getReferenceStatement());

        sqlResult       = SQLMatcher.match(userResultSet, refResultSet);

        if(sqlResult.isCorrect()) {
            Logger.debug("Statement is Correct");
        }

        return sqlResult;
    }

    /**
     *
     * @param sqlStatement
     * @return
     */
    private SQLResultSet executeStatement(String sqlStatement) {
        Statement           statement;
        ResultSet           rs;
        ResultSetMetaData   rsmd;
        List<SQLResultColumn>  resultSet = new ArrayList<>();
        SQLResultSet        sqlResultSet = new SQLResultSet();
        int                 columnCount;

        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery(sqlStatement);
            rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();

            // Create Header
            List<String> header = new ArrayList<>();
            for(int i = 1; i <= columnCount; i++) {
                SQLResultColumn sqlResultColumn;
                String columnAlias = rsmd.getColumnLabel(i);
                String columnName = ((!rsmd.getTableName(i).isEmpty()) ? rsmd.getTableName(i) + "." : "") + rsmd.getColumnName(i);
                String columnType = rsmd.getColumnTypeName(i);

                sqlResultColumn = new SQLResultColumn(columnAlias, columnName, columnType);

                resultSet.add(sqlResultColumn);
            }

            // Load data
            while(rs.next()) {
                for(int i = 1; i <= columnCount; i++) {
                    resultSet.get(i - 1).getData().add(rs.getString(i));
                }
            }

            statement.close();
            rs.close();

            sqlResultSet.setColumns(resultSet);
        } catch (SQLException e) {
            Logger.error("Submit SQL Statement nicht valide oder so");
            Logger.error(e.getMessage());
            sqlResultSet.setError(e.getMessage());
        }
        return sqlResultSet;
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            Logger.error("SQLParser Cannot Close Connection, what ever");
            Logger.warn(" - " + e.getSQLState());
            Logger.warn(" - " + e.getErrorCode());
        }
    }
}
