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
import java.util.ArrayList;
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
        Logger.info("Creating new Parser");
        if(taskTrial.getDatabaseUrl() != null && !taskTrial.getDatabaseUrl().isEmpty()) {
            Logger.warn(
                    String.format(
                            "Task Trial Object %d already have a database", taskTrial.getId()
                    )
            );
            return taskTrial;
        }
        String databaseUrl = getDatabaseUrl(taskTrial);

        taskTrial.setDatabaseUrl(databaseUrl);

        Logger.debug(databaseUrl);

        Connection connection = getConnection(databaseUrl);
        if(connection == null) {
            Logger.error("ParserFactory.createParser - didn't get a connection");
            return null;
        }

        SchemaDef schemaDef             = taskTrial.getTask().getSchemaDef();
        TableMaker tableMaker           = new TableMaker(schemaDef);
        ExtensionMaker extensionMaker   = new ExtensionMaker(taskTrial.getDatabaseExtensionSeed(), schemaDef);

        LocalDateTime startTime = LocalDateTime.now();

        CompletableFuture<ArrayList<String>> extensionMakerExtension =
                CompletableFuture.supplyAsync(extensionMaker::buildStatements);
        CompletableFuture<List<String>> tableMakerStatements =
                CompletableFuture.supplyAsync(tableMaker::buildStatement);


        try {
            List<String> createTableStatements = tableMakerStatements.get();
            ArrayList<String> extension = extensionMakerExtension.get();

            LocalDateTime endTime = LocalDateTime.now();
            Duration differenceTime = Duration.between(startTime, endTime);
            Logger.debug("Time Needed: " + differenceTime.toMillis() + " Millis");

            try {
                for(String createTableStatement : createTableStatements) {
                    Statement statement = connection.createStatement();
                    statement.execute(createTableStatement);
                    statement.close();
                }

                for(String extension1 : extension) {
                    Statement statement = connection.createStatement();
                    if(statement.execute(extension1)) {
                        Logger.info("ResultSet ftw");
                        ResultSet rs = statement.getResultSet();
                        rs.close();
                    }
                    statement.close();
                }
            } catch (SQLException e) {
                Logger.error("Failed while create ");
                Logger.error(e.getMessage());
            }


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

    /**
     *
     * @param taskTrial
     * @return
     */
    public SQLParser getParser(TaskTrial taskTrial) {
        if(taskTrial.getDatabaseUrl() == null || taskTrial.getDatabaseUrl().isEmpty()) {
            Logger.warn(String.format("TaskTrial Object %d has no Database ", taskTrial.getId()));
            return null;
        }

        Logger.debug("Found DB url: " + taskTrial.getDatabaseUrl());

        Connection connection = this.getConnection(taskTrial.getDatabaseUrl(), true);

        if(connection == null) {
            Logger.error("Cannot Create Database Connection");
            return null;
        }

        return new SQLParser(taskTrial, connection);
    }

    public void deleteDatabase(TaskTrial taskTrial) {
        if(taskTrial.getDatabaseUrl() == null || taskTrial.getDatabaseUrl().isEmpty()) {
            Logger.warn("TaskTrial Id: %d don't have a database");
            return;
        }
        String databasePath = this.getDatabasePath(taskTrial);
        String databaseName = this.getDatabaseName(taskTrial);
        DeleteDbFiles.execute(databasePath, databaseName, false);

        taskTrial.setDatabaseUrl(null);
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

    private String getDatabaseUrl(TaskTrial taskTrial) {
        return this.configuration.getString("sqlParser.urlPrefix")
                + this.getDatabasePath(taskTrial)
                + this.getDatabaseName(taskTrial);
    }

    private String getDatabasePath(TaskTrial taskTrial) {
        return this.configuration.getString("sqlParser.path");
    }

    private String getDatabaseName(TaskTrial taskTrial) {
        return taskTrial.getBeginDateFormat()
                + "-"
                + taskTrial.getTaskId()
                + "-"
                + taskTrial.getDatabaseExtensionSeed();
    }
}
