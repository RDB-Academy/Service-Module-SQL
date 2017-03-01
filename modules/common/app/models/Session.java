package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * @author fabiomazzone
 */
@Entity
public class Session extends BaseModel {
    @Id
    private String id;

    private Long userId;

    private String username;

    @JsonIgnore
    private int connectionInfo;

    @JsonIgnore
    @ManyToOne
    private TaskTrial taskTrial;

    public Session() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setConnectionInfo(int connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public int getConnectionInfo() {
        return connectionInfo;
    }

    public TaskTrial getTaskTrial() {
        return taskTrial;
    }

    public void setTaskTrial(TaskTrial taskTrial) {
        this.taskTrial = taskTrial;
    }

    public boolean isAdmin() {
        return this.username != null && this.username.equals("admin");
    }

    public boolean isValid() {
        return true;
    }
}
