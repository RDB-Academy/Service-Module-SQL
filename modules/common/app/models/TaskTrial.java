package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.submodels.DatabaseInformation;
import models.submodels.ResultSet;
import models.submodels.TaskTrialStats;
import parser.SQLResult;

import javax.persistence.*;

/**
 * The TaskTrial Class
 */
@Entity
public class TaskTrial extends BaseModel {
    @Id
    private Long                id;

    @Embedded
    @JsonProperty(value = "stats", access = JsonProperty.Access.READ_ONLY)
    public  TaskTrialStats      stats;

    @JsonIgnore
    @Embedded
    public  DatabaseInformation databaseInformation;

    private String userStatement;

    private boolean isCorrect;

    private boolean isFinished;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Task task;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Session session;

    @JsonIgnore
    @Transient
    private String error;

    @Transient
    @JsonProperty(value = "resultSet", access = JsonProperty.Access.READ_ONLY)
    public ResultSet resultSet;

    /**
     * The Constructor
     */
    public TaskTrial() {
        this.stats = new TaskTrialStats();
        this.databaseInformation = new DatabaseInformation();

        this.isCorrect = false;
        this.isFinished = false;
    }

    public Long getId() {
        return id;
    }

    public String getUserStatement() {
        return userStatement;
    }

    public void setUserStatement(String userStatement) {
        this.userStatement = userStatement;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    private void setIsCorrect(boolean correct) {
        isCorrect = correct;
        if(correct) {
            this.setIsFinished(true);
        }
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean finished) {
        isFinished = finished;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }







    public boolean hasError() {
        return this.error != null;
    }

    public String getError() {
        return error;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }
}
