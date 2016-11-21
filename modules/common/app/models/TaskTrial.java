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

    @JsonIgnore
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
    public Long getTaskId() {
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

    public void addTry() {
        this.tries++;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public String getBeginDateFormat() {
        return beginDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"));
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public String getSubmitDateFormat() {
        return submitDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"));
    }

    public void setSubmitDate(LocalDateTime submitDate) {
        this.submitDate = submitDate;
    }

}
