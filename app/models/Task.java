package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author fabiomazzone
 */
@Entity
public class Task extends BaseModel{
    @Id
    private long id;

    @Constraints.Required
    private String text;

    @Constraints.Required
    private String referenceStatement;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReferenceStatement() {
        return referenceStatement;
    }

    public void setReferenceStatement(String referenceStatement) {
        this.referenceStatement = referenceStatement;
    }
}
