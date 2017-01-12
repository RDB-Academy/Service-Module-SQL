package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * @author fabiomazzone
 */
@Entity
public class TaskTrialLog extends BaseModel {
    @Id
    private Long            id;

    @JsonIgnore
    @ManyToOne(optional = false)
    private TaskTrial       taskTrial;

    private String          statement;

    private boolean         isCorrect;

    private String          hintMessage;

    private String          errorMessage;

    private LocalDateTime   submittedAt;

    public Long getId() {
        return id;
    }

    public TaskTrial getTaskTrial() {
        return taskTrial;
    }

    public void setTaskTrial(TaskTrial taskTrial) {
        this.taskTrial = taskTrial;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getHintMessage() {
        return hintMessage;
    }

    public void setHintMessage(String hintMessage) {
        this.hintMessage = hintMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
