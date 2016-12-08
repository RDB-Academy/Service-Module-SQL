package parser;

import models.TaskTrial;
import play.Logger;

import javax.inject.Singleton;
import java.sql.*;
import java.util.concurrent.Future;

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

    public Future<SQLResult> submit(String userStatement) {
        Logger.info("Statement is: " + userStatement);

        Statement statement = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery(userStatement);
            ResultSetMetaData metaData = rs.getMetaData();
            Logger.info("Column Count " + metaData.getColumnCount());

            for(int i = 1; i <= metaData.getColumnCount(); i++) {
                Logger.info("ColumnName " + metaData.getColumnName(i));
                Logger.info("ColumnType " + metaData.getColumnTypeName(i));
            }
            while(rs.next()) {
                Logger.info("Row: " + rs.getRow());
                Logger.info("Data " + rs.getString(1));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
