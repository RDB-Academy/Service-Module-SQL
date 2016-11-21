package parser;

import com.google.inject.Inject;
import models.TaskTrial;
import parser.utils.extensionMaker.ExtensionMaker;
import play.Configuration;

import javax.inject.Singleton;
import java.sql.Connection;
import java.util.concurrent.*;

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
        String databaseDriver           = this.configuration.getString("sqlParser.driver");
        String databaseUrl              = this.getDatabaseUrl(taskTrial);
        Connection connection           = null;
        ExtensionMaker extensionMaker   = new ExtensionMaker(taskTrial.getDatabaseExtensionSeed());

        CompletableFuture<String[][]> extensions = CompletableFuture.completedFuture(extensionMaker
                .buildStatements(taskTrial.getTask().getSchemaDef()));

        try {
            Class.forName(databaseDriver);

            System.out.println(databaseUrl);
            System.out.println(databaseDriver);

            //connection = DriverManager.getConnection(databaseUrl);

            //System.out.println("Connection Established");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } /* finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } */
        // Get DB new Connection
        //- Build Extensions
        // Build Create Table Statements
        // Execute Create Table Statements
        //- Execute Extension Statements
        // Save DB
    }

    public CompletableFuture<SQLParser> getParser(TaskTrial taskTrial) {
        return CompletableFuture.completedFuture(getParserSingle(taskTrial));
    }

    private SQLParser getParserSingle(TaskTrial taskTrial) {
        return new SQLParser();
    }

    private String getDatabaseUrl(TaskTrial taskTrial) {
        return this.configuration.getString("sqlParser.urlPrefix")
                + taskTrial.getBeginDateFormat()
                + "-"
                + taskTrial.getTaskId()
                + "-"
                + taskTrial.getDatabaseExtensionSeed();
    }
}
