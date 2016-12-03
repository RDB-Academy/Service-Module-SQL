package parser;

import com.google.inject.Inject;
import models.SchemaDef;
import models.TaskTrial;
import parser.extensionMaker.ExtensionMaker;
import parser.tableMaker.TableMaker;
import play.Configuration;
import play.Logger;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author fabiomazzone
 */
@Singleton
public class SQLParserFactory {
    private final Configuration configuration;

    /**
     * This is the Constructor for the Factory
     * @param configuration the Play Configuration
     */
    @Inject
    public SQLParserFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public TaskTrial createParser(TaskTrial taskTrial) {
        String databaseUrl = this.configuration.getString("sqlParser.urlPrefix")
                + taskTrial.getBeginDateFormat()
                + "-"
                + taskTrial.getTaskId()
                + "-"
                + taskTrial.getDatabaseExtensionSeed();

        taskTrial.setDatabaseUrl(databaseUrl);

        System.out.println(databaseUrl);

        Connection connection = getConnection(databaseUrl);
        if(connection == null) {
            Logger.error("ParserFactory.createParser - didn't get a connection");
            return null;
        }

        SchemaDef schemaDef             = taskTrial.getTask().getSchemaDef();
        TableMaker tableMaker           = new TableMaker(schemaDef);
        ExtensionMaker extensionMaker   = new ExtensionMaker(taskTrial.getDatabaseExtensionSeed(), schemaDef);

        LocalDateTime startTime = LocalDateTime.now();

        CompletableFuture<String[][][]> extensionMakerExtension =
                CompletableFuture.supplyAsync(extensionMaker::buildStatements);
        CompletableFuture<List<String>> tableMakerStatements =
                CompletableFuture.supplyAsync(tableMaker::buildStatement);


        try {
            List<String> createTableStatements = tableMakerStatements.get();
            String[][][] extension = extensionMakerExtension.get();

            LocalDateTime endTime = LocalDateTime.now();
            Duration differenceTime = Duration.between(startTime, endTime);
            System.out.println("Time Needed: " + differenceTime.toMillis() + " Millis");

            // Run Statements

            connection.close();
        } catch (InterruptedException | ExecutionException e) {
            Logger.error("Cannot get Create Statement or Extension");
            e.printStackTrace();
        } catch (SQLException e) {
            Logger.error("Cannot Close Connection");
            e.printStackTrace();
        }
        return taskTrial;
    }

    public SQLParser getParser(TaskTrial taskTrial) {
        Connection connection = this.getConnection(taskTrial.getDatabaseUrl(), true);

        if(connection == null) {
            Logger.error("Cannot Create Database Connection");
            return null;
        }

        return new SQLParser(taskTrial, connection);
    }

    private Connection getConnection(String databaseUrl) {
        return getConnection(databaseUrl, false);
    }

    private Connection getConnection(String plainUrl, boolean ifExists) {
        String databaseDriver = this.configuration.getString("sqlParser.driver");
        String databaseUrl = plainUrl + ((ifExists) ? ";IFEXISTS=TRUE" : "");
        Connection connection = null;

        try {
            Class.forName(databaseDriver);
            connection = DriverManager.getConnection(databaseUrl);
        } catch (ClassNotFoundException e) {
            Logger.error("Parser cannot get Database Driver");
            Logger.error(" - " + e.getMessage());
        } catch (SQLException e) {
            Logger.error("Parser cannot connect to Database");
            Logger.error(" - " + e.getMessage());
        }

        return connection;
    }
}
