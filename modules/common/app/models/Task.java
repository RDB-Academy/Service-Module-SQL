package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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

    @NotNull
    @Constraints.Required
    private String name;

    @NotNull
    @Constraints.Required
    private String text;

    @NotNull
    @JsonIgnore
    @Constraints.Required
    private String referenceStatement;

    @NotNull
    @Constraints.Required
    private int difficulty;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if(this.name == null) {
            this.name = text;
        }
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

    @JsonGetter("schemaDef")
    public Long getSchemaDefId() {
        return this.getSchemaDef().getId();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
