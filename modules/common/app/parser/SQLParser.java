package parser;

import models.TaskTrial;
import play.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
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
