package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author fabiomazzone
 */
@Entity
public class Session extends BaseModel {
    @Id
    private String id;

    private Long userId;

    private String userName;

    public Session() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
