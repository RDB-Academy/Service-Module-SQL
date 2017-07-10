package sqlParser.sqlTrainerService.connection;

import models.sqlTrainerService.SchemaDef;
import models.sqlTrainerService.TaskTrial;
import org.h2.tools.DeleteDbFiles;
import sqlParser.sqlTrainerService.generators.ExtensionMaker;
import sqlParser.sqlTrainerService.generators.TableMaker;
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
public class DBConnectionFactory {

    /**
     * this function creates a parser and a database
     * @param taskTrial needs a TaskTrial Object
     * @return returns a updated TaskTrial Object
     */
    public TaskTrial createParser(@NotNull TaskTrial taskTrial) {
        Connection      connection;
        Statement       statement;

        SchemaDef       schemaDef;
        TableMaker      tableMaker;
        ExtensionMaker  extensionMaker;

        if(taskTrial.databaseInformation.getIsAvailable()) {
            Logger.warn(
                    String.format(
                            "Task Trial Object %d already have a database", taskTrial.getId()
                    )
            );
            return taskTrial;
        }

        connection = this.getConnection(taskTrial, false);

        if(connection == null) {
            Logger.error("ParserFactory.createParser - didn't get a connection");
            return null;
        }

        schemaDef       = taskTrial.getTask().getSchemaDef();
        tableMaker      = new TableMaker(schemaDef);
        extensionMaker  = new ExtensionMaker(
                taskTrial.databaseInformation.getSeed(),
                schemaDef,
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

                // Insert Generated Extensions
                for(String extension : genExtensionList) {
                    if(statement.execute(extension)) {
                        Logger.error("ResultSet wtf?!?");
                        ResultSet rs = statement.getResultSet();
                        rs.close();
                    }
                }

                statement.close();
            } catch (SQLException e) {
                Logger.error("Failed while create extension oder table");
                Logger.error("DatabaseSeed: " + taskTrial.databaseInformation.getSeed());
                Logger.error("#" + schemaDef.getId() + "-" + schemaDef.getName());
                Logger.error("Message: " + e.getSQLState());
                Logger.error(e.getMessage());
            }

            // StopWatch
            LocalDateTime endTime = LocalDateTime.now();
            Duration differenceTime = Duration.between(startTime, endTime);
            Logger.debug("Time Needed: " + differenceTime.toMillis() + " Millis");

            connection.close();
            taskTrial.databaseInformation.setIsAvailable(true);
        }  catch (InterruptedException | ExecutionException e) {
            Logger.error("Cannot get create \"CreateTableStatement\" or \"Extension\"");
            Logger.error(e.getMessage());
        } catch (SQLException e) {
            Logger.error("Cannot close connection");
            Logger.error(e.getMessage());
        }
        return taskTrial;
    }

    /**
     * This functions returns a the sql parser for the task Trial Object
     * @param taskTrial needs a task trial object
     * @return returns a sqlParser
     */
    public DBConnection getParser(@NotNull TaskTrial taskTrial) {
        if(!taskTrial.databaseInformation.getIsAvailable()) {
            Logger.warn(String.format("TaskTrial Object %d has no Database ", taskTrial.getId()));
            return null;
        }

        Logger.debug("Found DB url: " + taskTrial.databaseInformation.getUrl());

        java.sql.Connection connection = this.getConnection(taskTrial, true);

        if(connection == null) {
            Logger.error("Cannot Create Database DBConnection");
            return null;
        }

        return new DBConnection(taskTrial, connection);
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

    /**
     *
     * @param taskTrial the taskTrial object
     * @param ifExists if Exist
     * @return returns connection
     */
    private java.sql.Connection getConnection(TaskTrial taskTrial, boolean ifExists) {
        String      databaseDriver;
        String      databasePath;
        java.sql.Connection connection = null;

        databaseDriver = taskTrial.databaseInformation.getDriver();
        databasePath = taskTrial.databaseInformation.getUrl() + ((ifExists) ? ";IFEXISTS=TRUE" : "");

        try {
            Class.forName(databaseDriver);
            connection = DriverManager.getConnection(databasePath);
        } catch (ClassNotFoundException e) {
            Logger.error("factory cannot get Database Driver");
            Logger.error(e.getMessage());
        } catch (SQLException e) {
            Logger.error("factory cannot connect to Database");
            Logger.error(e.getMessage());
        }

        return connection;
    }
}
