package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by invisible on 11/11/16.
 */
public class TaskTrial extends BaseModel {
    @Id
    private long id;

    @ManyToOne
    private Task task;

    @JsonIgnore
    private long databaseCreationSeed;

    private String userStatement;

    private boolean isCorrect;

    @JsonIgnore
    private Date beginDate;

    @JsonIgnore
    private Date submitDate;

    public long getId() {
        return id;
    }

    @JsonIgnore
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public long getDatabaseCreationSeed() {
        return databaseCreationSeed;
    }

    public void setDatabaseCreationSeed(long databaseCreationSeed) {
        this.databaseCreationSeed = databaseCreationSeed;
    }

    public String getUserStatement() {
        return userStatement;
    }

    public void setUserStatement(String userStatement) {
        this.userStatement = userStatement;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }
}
