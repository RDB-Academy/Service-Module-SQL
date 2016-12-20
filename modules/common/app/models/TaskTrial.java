package models;

import com.avaje.ebean.annotation.WhenCreated;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import parser.SQLResult;
import play.libs.Json;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author invisible
 */
@Entity
public class TaskTrial extends BaseModel {
    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Task task;

    @Embedded
    private TaskTrialStats stats;

    private String userStatement;

    private boolean isCorrect = false;

    private boolean isFinished = false;

    @JsonIgnore
    private String databaseUrl;

    @JsonIgnore
    private long databaseExtensionSeed;

    @JsonIgnore
    @Transient
    private SQLError error;

    @Transient
    @JsonIgnore
    private SQLResult sqlResult;

    public TaskTrial() {
        this.stats = new TaskTrialStats();
    }

    public String getError() {
        return error.message;
    }

    private class SQLError {
        private final String message;

        SQLError(String message) {
            this.message = message;
        }
    }

    public Long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    @JsonGetter("task")
    public Long getTaskId() {
        return this.getTask().getId();
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskTrialStats getStats() {
        return this.stats;
    }

    public long getDatabaseExtensionSeed() {
        return databaseExtensionSeed;
    }

    public void setDatabaseExtensionSeed(long databaseExtensionSeed) {
        this.databaseExtensionSeed = databaseExtensionSeed;
    }

    public String getUserStatement() {
        return userStatement;
    }

    public void setUserStatement(String userStatement) {
        this.userStatement = userStatement;
    }

    @JsonGetter("isCorrect")
    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
        if(correct) {
            this.setFinished(true);
        }
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public boolean isFinished() {
        return isFinished;
    }

    @JsonSetter("isFinished")
    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean hasError() {
        return this.error != null;
    }


    public void addError(String message) {
        this.error = new SQLError(message);
    }

    @JsonIgnore
    public JsonNode errorsAsJson() {
        ObjectNode errorNode = Json.newObject();

        errorNode.put("errorMessage", this.error.message);

        return errorNode;
    }

    @JsonIgnore
    public void setSqlResult(SQLResult sqlResultSet) {
        this.sqlResult = sqlResultSet;
        this.setCorrect(sqlResult.isCorrect());
    }

    @JsonGetter("resultSet")
    public SQLResult getSqlResult() {
        return sqlResult;
    }

}
