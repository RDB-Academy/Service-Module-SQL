package models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by invisible on 11/10/16.
 */
@Entity
public class ForeignKey extends BaseModel {
    @Id
    Long id;

    @ManyToOne
    SchemaDef schemaDef;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foreignKey")
    List<ForeignKeyRelation> foreignKeyRelationList;

    public long getId() {
        return id;
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
}
