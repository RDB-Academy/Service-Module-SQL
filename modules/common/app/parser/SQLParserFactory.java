package parser;

import models.TaskTrial;

import javax.inject.Singleton;
import java.util.concurrent.Future;

/**
 * @author fabiomazzone
 */
@Singleton
public class SQLParserFactory {
    public void createParser(TaskTrial taskTrial) {
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
}
