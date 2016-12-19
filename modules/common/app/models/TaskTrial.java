package models;

import com.avaje.ebean.annotation.WhenCreated;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
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

    @JsonIgnore
    private String databaseUrl;

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

    @Transient
    private SQLError error;

    @Transient
    @JsonIgnore
    private SQLResult.SQLResultSet sqlResultSet;
    private boolean finished;

    public void setFinished(boolean finished) {
        this.finished = finished;
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

    @JsonIgnore
    public String getBeginDateFormat() {
        return beginDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"));
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    @JsonIgnore
    public String getSubmitDateFormat() {
        return submitDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm"));
    }

    public void setSubmitDate(LocalDateTime submitDate) {
        this.submitDate = submitDate;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public void addError(String message) {
        this.error = new SQLError(message);
    }

    public JsonNode errorsAsJson() {
        return Json.toJson(this.error);
    }

    @JsonIgnore
    public void setSqlResultSet(SQLResult.SQLResultSet sqlResultSet) {
        this.sqlResultSet = sqlResultSet;
    }

    @JsonGetter("resultSet")
    public SQLResult.SQLResultSet getSqlResultSet() {
        return sqlResultSet;
    }

}
