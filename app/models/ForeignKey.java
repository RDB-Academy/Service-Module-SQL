package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author invisible
 */
@Entity
public class ForeignKey extends BaseModel {
    @Id
    private Long id;

    private String name;

    @ManyToOne
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
