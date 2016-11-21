package parser;

import com.google.inject.Inject;
import models.TaskTrial;
import play.Configuration;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Future;

/**
 * @author fabiomazzone
 */
@Singleton
public class SQLParserFactory {
    private final Configuration configuration;

    @Inject
    public SQLParserFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public void createParser(TaskTrial taskTrial) {
        String databaseDriver   = this.configuration.getString("sqlParser.driver");
        String databaseUrl      = this.getDatabaseUrl(taskTrial);
        Connection connection   = null;


        try {
            Class.forName(databaseDriver);

            System.out.println(databaseUrl);
            System.out.println(databaseDriver);

            connection = DriverManager.getConnection(databaseUrl);

            System.out.printf("Connection Established");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Get DB new Connection
        //- Build Extensions
        // Build Create Table Statements
        // Execute Create Table Statements
        //- Execute Extension Statements
        // Save DB
    }

    public Future<SQLParser> getParser(TaskTrial taskTrial) {
        // Get DB Connection
        // Create a New SQL Parser Object
        // Return the object
        return null;
    }

    private String getDatabaseUrl(TaskTrial taskTrial) {
        return this.configuration.getString("sqlParser.urlPrefix")
                + taskTrial.getTaskId()
                + "-"
                + taskTrial.getDatabaseExtensionSeed()
                + "-"
                + taskTrial.getBeginDateFormat();
    }
}
