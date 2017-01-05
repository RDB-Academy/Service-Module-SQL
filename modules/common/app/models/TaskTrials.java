package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.submodels.DatabaseInformation;
import models.submodels.ResultSet;
import models.submodels.TaskTrialStats;

import javax.persistence.*;

/**
 * The TaskTrials Class
 */
@Entity
public class TaskTrials extends BaseModel {
    @Id
    private Long                id;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Task task;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Session session;




    @Embedded
    @JsonProperty(value = "stats", access = JsonProperty.Access.READ_ONLY)
    public  TaskTrialStats      stats;

    @Embedded
    @JsonIgnore
    public  DatabaseInformation databaseInformation;

    @Transient
    @JsonProperty(value = "resultSet", access = JsonProperty.Access.READ_ONLY)
    private ResultSet           resultSet;

    private String              userStatement;
    private boolean             isCorrect;
    private boolean             isFinished;

    /**
     * The Constructor
     */
    public TaskTrials() {
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

    public void setIsCorrect(boolean correct) {
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

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
}