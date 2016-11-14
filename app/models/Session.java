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
    String id;

    public Session() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}
