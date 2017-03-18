package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.submodels.DatabaseInformation;
import models.submodels.ResultSet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The TaskTrial Class
 */
@Entity
public class TaskTrial extends BaseModel {
    @Id
    private Long                id;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Task                task;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Session             session;

    @JsonIgnore
    @OneToMany(mappedBy = "taskTrial", cascade = CascadeType.ALL)
    private List<TaskTrialLog>  taskTrialLogList;

    @Embedded
    @JsonIgnore
    public  DatabaseInformation databaseInformation;

    @Transient
    @JsonProperty(value = "resultSet", access = JsonProperty.Access.READ_ONLY)
    private ResultSet           resultSet;

    private boolean             isFinished;

    /**
     * The Constructor
     */
    public TaskTrial() {
        this.databaseInformation= new DatabaseInformation();
        this.taskTrialLogList   = new ArrayList<>();

        this.isFinished = false;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private List<TaskTrialLog> getTaskTrialLogList() {
        return taskTrialLogList;
    }

    @JsonGetter("tries")
    public int getTries() {
        return this.getTaskTrialLogList().size();
    }

    @JsonGetter("taskTrialStatus")
    public TaskTrialLog getTaskTrialStatus() {
        if(this.taskTrialLogList == null || this.taskTrialLogList.size() == 0) {
            return new TaskTrialLog();
        }

        TaskTrialLog taskTrialLog = this.taskTrialLogList.get(0);
        for (TaskTrialLog taskTrialLogIterator : this.taskTrialLogList) {
            if(taskTrialLogIterator.getCreatedAt().isAfter(taskTrialLog.getCreatedAt())) {
                taskTrialLog = taskTrialLogIterator;
            }
        }

        return taskTrialLog;
    }

    public void setTaskTrialLogList(List<TaskTrialLog> taskTrialLogList) {
        this.taskTrialLogList = taskTrialLogList;
    }

    public void addTaskTrialLog(TaskTrialLog taskTrialLog) {
        if(this.taskTrialLogList == null) {
            this.taskTrialLogList = new ArrayList<>();
        }
        taskTrialLog.setTaskTrial(this);
        this.taskTrialLogList.add(taskTrialLog);
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean finished) {
        isFinished = finished;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
}
