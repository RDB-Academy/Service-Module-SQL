package models;

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

    @ManyToOne(optional = false)
    private TaskTrial       taskTrial;

    private String          statement;

    private boolean         isCorrect;

    private String          hintMessage;

    private String          errorMessage;

    private LocalDateTime   submitted;

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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
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

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }
}
