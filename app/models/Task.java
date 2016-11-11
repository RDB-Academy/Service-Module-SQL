package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
    @JsonIgnore
    private String referenceStatement;

    @ManyToOne
    private SchemaDef schemaDef;

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

    public SchemaDef getSchemaDef() {
        return schemaDef;
    }

    public void setSchemaDef(SchemaDef schemaDef) {
        this.schemaDef = schemaDef;
    }
}
