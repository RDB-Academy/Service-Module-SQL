package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.submodels.DatabaseInformation;
import models.submodels.TaskTrialStats;
import parser.SQLResult;
import play.libs.Json;

import javax.persistence.*;

/**
 * The TaskTrial Class
 */
@Entity
public class TaskTrial extends BaseModel {
    @Id
    private Long                id;
    @Embedded
    public  TaskTrialStats      stats;
    @Embedded @JsonIgnore
    public  DatabaseInformation databaseInformation;




    private String userStatement;

    private boolean isCorrect = false;

    private boolean isFinished = false;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Task task;

    @JsonIgnore
    @Transient
    private SQLError error;

    @Transient
    @JsonIgnore
    private SQLResult sqlResult;

    public TaskTrial() {
        this.stats = new TaskTrialStats();
        this.databaseInformation = new DatabaseInformation();
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
