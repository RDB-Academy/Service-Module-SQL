package models.sqlTrainerService;

import models.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * @author fabiomazzone
 * @version 1.0, 06.07.17
 */
@Entity
public class UserData extends BaseModel {
    @Id
    Long id;

    @OneToMany(mappedBy = "userData")
    List<TaskTrial> doneTaskTrials;

    @OneToOne
    TaskTrial currentTaskTrial;

    public List<TaskTrial> getDoneTaskTrials() {
        return doneTaskTrials;
    }

    public void setDoneTaskTrials(List<TaskTrial> doneTaskTrials) {
        this.doneTaskTrials = doneTaskTrials;
    }

    public TaskTrial getCurrentTaskTrial() {
        return currentTaskTrial;
    }

    public void setCurrentTaskTrial(TaskTrial currentTaskTrial) {
        this.currentTaskTrial = currentTaskTrial;
    }
}
