package parser;

import models.SchemaDef;
import models.TaskTrials;
import org.h2.tools.DeleteDbFiles;
import parser.extensionMaker.ExtensionMaker;
import parser.tableMaker.TableMaker;
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

    /**
     * this function creates a parser and a database
     * @param taskTrials needs a TaskTrials Object
     * @return returns a updated TaskTrials Object
     */
    public TaskTrials createParser(@NotNull TaskTrials taskTrials) {
        Connection      connection;
        SchemaDef       schemaDef;
        TableMaker      tableMaker;
        ExtensionMaker  extensionMaker;

        Statement       statement;

        Logger.debug("Creating new Parser");
        if(taskTrials.databaseInformation.getIsAvailable()) {
            Logger.warn(
                    String.format(
                            "Task Trial Object %d already have a database", taskTrials.getId()
                    )
            );
            return taskTrials;
        }

        connection = this.getConnection(taskTrials, false);

        if(connection == null) {
            Logger.error("ParserFactory.createParser - didn't get a connection");
            return null;
        }

        schemaDef       = taskTrials.getTask().getSchemaDef();
        tableMaker      = new TableMaker(schemaDef);
        extensionMaker  = new ExtensionMaker(
                taskTrials.databaseInformation.getSeed(),
                schemaDef,
                0,
                75,
                150
        );

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

            // StopWatch
            LocalDateTime endTime = LocalDateTime.now();
            Duration differenceTime = Duration.between(startTime, endTime);
            Logger.debug("Time Needed: " + differenceTime.toMillis() + " Millis");

            connection.close();
            taskTrials.databaseInformation.setIsAvailable(true);
        } catch (InterruptedException | ExecutionException e) {
            Logger.error("Cannot get Create Statement or Extension");
            Logger.error(e.getMessage());
        } catch (SQLException e) {
            Logger.error("Cannot close Connection or something similar");
            Logger.error(e.getMessage());
        }
        return taskTrials;
    }

    /**
     * This functions returns a the sql parser for the task Trial Object
     * @param taskTrials needs a task trial object
     * @return returns a sqlParser
     */
    public SQLParser getParser(@NotNull TaskTrials taskTrials) {
        if(!taskTrials.databaseInformation.getIsAvailable()) {
            Logger.warn(String.format("TaskTrials Object %d has no Database ", taskTrials.getId()));
            return null;
        }

        Logger.debug("Found DB url: " + taskTrials.databaseInformation.getUrl());

        Connection connection = this.getConnection(taskTrials, true);

        if(connection == null) {
            Logger.error("Cannot Create Database Connection");
            return null;
        }

        return new SQLParser(taskTrials, connection);
    }

    public void deleteDatabase(TaskTrials taskTrials) {
        if(!taskTrials.databaseInformation.getIsAvailable()) {
            Logger.warn("TaskTrials Id: %d don't have a database");
            return;
        }
        String databasePath = taskTrials.databaseInformation.getPath();
        String databaseName = taskTrials.databaseInformation.getName();
        DeleteDbFiles.execute(databasePath, databaseName, false);

        taskTrials.databaseInformation.setIsAvailable(false);
    }

    /**
     *
     * @param taskTrials the taskTrials object
     * @param ifExists if Exist
     * @return returns connection
     */
    private Connection getConnection(TaskTrials taskTrials, boolean ifExists) {
        String      databaseDriver;
        String      databasePath;
        Connection  connection = null;

        databaseDriver = taskTrials.databaseInformation.getDriver();
        databasePath = taskTrials.databaseInformation.getUrl() + ((ifExists) ? ";IFEXISTS=TRUE" : "");


        try {
            Class.forName(databaseDriver);
            connection = DriverManager.getConnection(databasePath);
        } catch (ClassNotFoundException e) {
            Logger.error("Parser cannot get Database Driver");
            Logger.error(e.getMessage());
        } catch (SQLException e) {
            Logger.error("Parser cannot connect to Database");
            Logger.error(e.getMessage());
        }

        return connection;
    }
}
