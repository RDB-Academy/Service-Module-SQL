package parser;

import com.google.inject.Inject;
import models.TaskTrial;
import parser.extensionMaker.ExtensionMaker;
import parser.tableMaker.TableMaker;
import play.Configuration;

import javax.inject.Singleton;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

        TableMaker tableMaker           = new TableMaker(taskTrial.getTask().getSchemaDef());
        ExtensionMaker extensionMaker   = new ExtensionMaker(taskTrial.getDatabaseExtensionSeed(), taskTrial.getTask().getSchemaDef());

        LocalDateTime startTime = LocalDateTime.now();
        CompletableFuture<String[][]> extensions = CompletableFuture
                .completedFuture(
                        extensionMaker.buildStatements()
                );

        CompletableFuture<String[]> tableMakerStatements = CompletableFuture
                .completedFuture(
                        tableMaker.buildStatements()
                );


        try {
            extensions.get();
            tableMakerStatements.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        LocalDateTime endTime = LocalDateTime.now();

        Duration differenceTime = Duration.between(startTime, endTime);
        System.out.println("Time Needed: " + differenceTime.toMillis() + " Millis");

        /*try {
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
