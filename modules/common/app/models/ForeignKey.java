package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author invisible
 */
@Entity
public class ForeignKey extends BaseModel {
    @Id
    private Long id;

    @NotNull
    @Constraints.Required
    private String name;

    @Constraints.Required
    @ManyToOne(optional = false)
    private SchemaDef schemaDef;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foreignKey")
    private List<ForeignKeyRelation> foreignKeyRelationList;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchemaDef getSchemaDef() {
        return schemaDef;
    }

    public void setSchemaDef(SchemaDef schema) {
        this.schemaDef = schema;
    }

    public List<ForeignKeyRelation> getForeignKeyRelationList() {
        return foreignKeyRelationList;
    }

    public void setForeignKeyRelationList(List<ForeignKeyRelation> foreignKeyRelationList) {
        this.foreignKeyRelationList = foreignKeyRelationList;
    }

    public void addForeignKeyRelation(ForeignKeyRelation foreignKeyRelation) {
        if (this.foreignKeyRelationList == null) {
            this.foreignKeyRelationList = new ArrayList<>();
        }

        if(!this.foreignKeyRelationList.contains(foreignKeyRelation)) {
            this.foreignKeyRelationList.add(foreignKeyRelation);
        }
    }
}
