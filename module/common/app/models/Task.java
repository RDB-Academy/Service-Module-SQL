package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author fabiomazzone
 */
@Entity
public class Task extends BaseModel{
    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    private SchemaDef schemaDef;

    @Constraints.Required
    private String name;

    @Constraints.Required
    private String text;

    @JsonIgnore
    @Constraints.Required
    private String referenceStatement;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @JsonGetter("schema")
    public long getSchemaDefId() {
        return this.getSchemaDef().getId();
    }
}
