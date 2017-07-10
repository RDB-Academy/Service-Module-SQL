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

    @OneToMany(mappedBy = "userProfile", cascade =  CascadeType.ALL)
    private List<Session> sessionList;

    @OneToOne(cascade = CascadeType.ALL)
    private Session currentSession;

    /**
     * UserProfile Constructor
     */
    public UserProfile() {
        this.sessionList = new ArrayList<>();
    }
}
