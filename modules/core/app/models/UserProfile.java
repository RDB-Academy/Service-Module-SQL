package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Entity
public class UserProfile extends BaseModel {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "userProfile", cascade =  CascadeType.ALL)
    private List<Session> sessionList;

    //@OneToMany(cascade = CascadeType.ALL)
    //private Session currentSession;

    @ManyToOne(cascade = CascadeType.ALL)
    private TaskTrial currentTaskTrial;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<TaskTrial> taskTrialList;

    /**
     * UserProfile Constructor
     */
    public UserProfile() {
        this.sessionList = new ArrayList<>();
        this.taskTrialList = new ArrayList<>();
    }

    public TaskTrial getCurrentTaskTrial() {
        return currentTaskTrial;
    }

    public void setCurrentTaskTrial(TaskTrial currentTaskTrial) {
        this.currentTaskTrial = currentTaskTrial;

        this.taskTrialList.add(currentTaskTrial);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
