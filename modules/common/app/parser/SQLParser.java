package parser;

import models.TaskTrial;
import play.Logger;

import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

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

    public SQLResult submit(String userStatement) {
        Statement statement = null;
        SQLResult sqlResult = null;
        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet rs = statement.executeQuery(userStatement);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<List<String>> resultSet = new ArrayList<>();


            // Build Header
            List<String> headerList = new ArrayList<>();
            for(int i = 1; i <= columnCount; i++) {
                headerList.add(metaData.getColumnName(i));
            }
            resultSet.add(headerList);

            while(rs.next()) {
                List<String> rowList = new ArrayList<>();
                for(int i = 1; i <= columnCount; i++) {
                    rowList.add(rs.getString(i));
                }
                resultSet.add(rowList);
            }

            rs.close();
            statement.close();

            for(List<String> row : resultSet) {
                for(String column : row) {
                    System.out.print(column + " | ");
                }
                System.out.println();
            }

            sqlResult = new SQLResult(resultSet, false);
            return sqlResult;
        } catch (SQLException e) {
            Logger.error("SQL Statement nicht valide oder so");
            Logger.error(e.getMessage());
            return null;
        }
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
