package models;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * @author fabiomazzone
 */
public class TaskTrialLog extends BaseModel {
    @Id
    private Long            id;

    @ManyToOne(optional = false)
    private TaskTrial       taskTrial;

    private String          statement;

    private LocalDateTime   submitted;

    private String          hintMessage;

    private String          errorMessage;

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

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
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
}
