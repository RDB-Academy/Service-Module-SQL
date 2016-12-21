package parser;

import models.TaskTrial;
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
    public SQLResult submit(TaskTrial taskTrial) {
        SQLResult       sqlResult;
        SQLResultSet    userResultSet;
        SQLResultSet    refResultSet;

        userResultSet   = executeStatement(taskTrial.getUserStatement());
        refResultSet    = executeStatement(taskTrial.getTask().getReferenceStatement());
        sqlResult       = new SQLResult(userResultSet, refResultSet.isSubsetOf(userResultSet));
        if(sqlResult.isCorrect()) {
            Logger.debug("Statement is Correct");
        }
        /*
        // Log UserResultSet
        System.out.println("UserResultSet");
        for(List<String> row : userResultSet.getResultSet()) {
            for(String column : row) {
                System.out.print(column + " | ");
            }
            System.out.println();
        }

        // Log RefResultSet
        System.out.println("RefResultSet");
        for(List<String> row : refResultSet.getResultSet()) {
            for(String column : row) {
                System.out.print(column + " | ");
            }
            System.out.println();
        }
        */



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
        List<List<String>>  resultSet = new ArrayList<>();
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
                header.add(
                        ((!rsmd.getTableName(i).isEmpty()) ? rsmd.getTableName(i) + "." : "")
                                + rsmd.getColumnName(i));
            }
            resultSet.add(header);

            // Load data
            while(rs.next()) {
                List<String> rowList = new ArrayList<>();
                for(int i = 1; i <= columnCount; i++) {
                    rowList.add(rs.getString(i));
                }
                resultSet.add(rowList);
            }

            statement.close();
            rs.close();

            sqlResultSet.setResultSet(resultSet);
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
