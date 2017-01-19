package sqlParser.connection;

import models.TaskTrial;
import models.TaskTrialLog;
import sqlParser.SQLResult;
import sqlParser.SQLResultColumn;
import sqlParser.SQLResultSet;
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
public class DBConnection {
    private final TaskTrial taskTrial;
    private final java.sql.Connection connection;

    /**
     *
     * @param taskTrial     the taskTrial object
     * @param connection    the database connection
     */
    DBConnection(
            TaskTrial taskTrial,
            java.sql.Connection connection   ) {

        this.taskTrial = taskTrial;
        this.connection = connection;
    }

    /**
     *
     * @param taskTrialLog the taskTrialLog object
     * @return returns an sql result
     */
    public SQLResult submit(TaskTrialLog taskTrialLog) {
        SQLResult       sqlResult;
        SQLResultSet userResultSet;
        SQLResultSet    refResultSet;

        userResultSet   = executeStatement(taskTrialLog.getStatement());
        refResultSet    = executeStatement(this.taskTrial.getTask().getReferenceStatement());

        sqlResult       = SQLMatcher.match(userResultSet, refResultSet);

        return sqlResult;
    }

    /**
     *
     * @param sqlStatement the sqlStatement that should be executed
     * @return  returns an sqlResultSet
     */
    private SQLResultSet executeStatement(String sqlStatement) {
        Statement               statement;
        ResultSet               resultSet;
        ResultSetMetaData       resultSetMetaData;
        List<SQLResultColumn>   sqlResultColumnList = new ArrayList<>();
        SQLResultSet            sqlResultSet = new SQLResultSet();
        int                     columnCount;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);
            resultSetMetaData = resultSet.getMetaData();
            columnCount = resultSetMetaData.getColumnCount();

            for(int i = 1; i <= columnCount; i++) {
                SQLResultColumn sqlResultColumn;
                String columnAlias = resultSetMetaData.getColumnLabel(i);
                String columnName = ((!resultSetMetaData.getTableName(i).isEmpty()) ? resultSetMetaData.getTableName(i) + "." : "") + resultSetMetaData.getColumnName(i);
                String columnType = resultSetMetaData.getColumnTypeName(i);

                sqlResultColumn = new SQLResultColumn(columnAlias, columnName, columnType);

                sqlResultColumnList.add(sqlResultColumn);
            }

            // Load data
            while(resultSet.next()) {
                for(int i = 1; i <= columnCount; i++) {
                    sqlResultColumnList.get(i - 1).getData().add(resultSet.getString(i));
                }
            }

            statement.close();
            resultSet.close();

            sqlResultSet.setColumns(sqlResultColumnList);
        } catch (SQLException e) {
            Logger.error("Submitted SQL Statement is not executable");
            Logger.error(e.getMessage());
            sqlResultSet.setErrorMessage(e.getMessage());
        }
        return sqlResultSet;
    }

    /**
     * this functions closes the connection to the database
     */
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            Logger.error("DBConnection Cannot Close DBConnection, what ever");
            Logger.warn(" - " + e.getSQLState());
            Logger.warn(" - " + e.getErrorCode());
        }
    }
}
