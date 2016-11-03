package models;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author fabiomazzone
 */
@Entity
public class Task extends BaseModel{
    @Id
    private long id;

    private String text;

    private String referenceStatement;
}
