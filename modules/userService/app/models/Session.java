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

    @JsonIgnore
    @ManyToOne(optional = false)
    private UserProfile userProfile;

    public Session() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public boolean isValid() {
        return true;
    }
}
