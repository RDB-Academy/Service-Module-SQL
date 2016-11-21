package models;

import com.avaje.ebean.annotation.WhenCreated;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author invisible
 */
@Entity
public class TaskTrial extends BaseModel {
    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @Column(updatable = false)
    private Task task;

    @JsonIgnore
    @Column(updatable = false)
    private long databaseExtensionSeed;

    private String userStatement;

    private boolean isCorrect = false;

    private int tries = 0;

    @NotNull
    @WhenCreated
    @Column(updatable = false)
    private LocalDateTime beginDate;

    private LocalDateTime submitDate;



    public Long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    @JsonGetter("task")
    public long getTaskId() {
        return this.getTask().getId();
    }

    public void setTask(Task task) {
        this.task = task;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDateTime submitDate) {
        this.submitDate = submitDate;
    }

}
