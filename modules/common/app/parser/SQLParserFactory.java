package parser;

import com.google.inject.Inject;
import models.SchemaDef;
import models.TaskTrial;
import org.h2.tools.DeleteDbFiles;
import parser.extensionMaker.ExtensionMaker;
import parser.tableMaker.TableMaker;
import play.Configuration;
import play.Logger;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.sql.*;
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

    /**
     * this function creates a parser and a database
     * @param taskTrial needs a TaskTrial Object
     * @return returns a updated TaskTrial Object
     */
    public TaskTrial createParser(@NotNull TaskTrial taskTrial) {
        String          databaseUrl;
        Connection      connection;
        SchemaDef       schemaDef;
        TableMaker      tableMaker;
        ExtensionMaker  extensionMaker;

        Statement       statement;

        Logger.debug("Creating new Parser");
        if(taskTrial.databaseInformation.getIsAvailable()) {
            Logger.warn(
                    String.format(
                            "Task Trial Object %d already have a database", taskTrial.getId()
                    )
            );
            return taskTrial;
        }

        connection = this.getConnection(taskTrial.databaseInformation.getUrl());

        if(connection == null) {
            Logger.error("ParserFactory.createParser - didn't get a connection");
            return null;
        }

        schemaDef       = taskTrial.getTask().getSchemaDef();
        tableMaker      = new TableMaker(schemaDef);
        extensionMaker  = new ExtensionMaker(taskTrial.databaseInformation.getSeed(), schemaDef);

        // StopWatch
        LocalDateTime startTime = LocalDateTime.now();

        // Start Makers
        CompletableFuture<List<String>> extensionMakerExtension =
                CompletableFuture.supplyAsync(extensionMaker::buildStatements);
        CompletableFuture<List<String>> tableMakerStatements =
                CompletableFuture.supplyAsync(tableMaker::buildStatement);

        try {
            // Get Result
            List<String> createTableStatements = tableMakerStatements.get();
            List<String> genExtensionList = extensionMakerExtension.get();

            // StopWatch
            LocalDateTime endTime = LocalDateTime.now();
            Duration differenceTime = Duration.between(startTime, endTime);
            Logger.debug("Time Needed: " + differenceTime.toMillis() + " Millis");

            try {
                statement = connection.createStatement();

                // Create Tables
                for(String createTableStatement : createTableStatements) {
                    statement.execute(createTableStatement);
                }

                // Insert Static Extensions
                // ToDo

                // Insert Generated Extensions
                for(String extension : genExtensionList) {
                    if(statement.execute(extension)) {
                        Logger.info("ResultSet ftw");
                        ResultSet rs = statement.getResultSet();
                        rs.close();
                    }
                }

                statement.close();
            } catch (SQLException e) {
                Logger.error("Failed while create ");
                Logger.error(e.getMessage());
            }

            connection.close();
            taskTrial.databaseInformation.setIsAvailable(true);
        } catch (InterruptedException | ExecutionException e) {
            Logger.error("Cannot get Create Statement or Extension");
            Logger.error(e.getMessage());
        } catch (SQLException e) {
            Logger.error("Cannot close Connection or something similar");
            Logger.error(e.getMessage());
        }
        return taskTrial;
    }

    /**
     * This functions returns a the sql parser for the task Trial Object
     * @param taskTrial needs a task trial object
     * @return returns a sqlParser
     */
    public SQLParser getParser(@NotNull TaskTrial taskTrial) {
        if(!taskTrial.databaseInformation.getIsAvailable()) {
            Logger.warn(String.format("TaskTrial Object %d has no Database ", taskTrial.getId()));
            return null;
        }

        Logger.debug("Found DB url: " + taskTrial.databaseInformation.getUrl());

        Connection connection = this.getConnection(taskTrial.databaseInformation.getUrl(), true);

        if(connection == null) {
            Logger.error("Cannot Create Database Connection");
            return null;
        }

        return new SQLParser(taskTrial, connection);
    }

    public void deleteDatabase(TaskTrial taskTrial) {
        if(!taskTrial.databaseInformation.getIsAvailable()) {
            Logger.warn("TaskTrial Id: %d don't have a database");
            return;
        }
        String databasePath = taskTrial.databaseInformation.getPath();
        String databaseName = taskTrial.databaseInformation.getName();
        DeleteDbFiles.execute(databasePath, databaseName, false);

        taskTrial.databaseInformation.setIsAvailable(false);
    }

    private Connection getConnection(String databaseUrl) {
        return getConnection(databaseUrl, false);
    }

    /**
     * Private blabla
     * @param plainUrl
     * @param ifExists
     * @return
     */
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
