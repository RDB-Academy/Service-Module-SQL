package models;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by invisible on 11/10/16.
 */
public class ForeignKey extends BaseModel {
    @Id
    long id;

    @ManyToOne
    SchemaDef schema;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foreignKey")
    List<ForeignKeyRelation> foreignKeyRelationList;

    public long getId() {
        return id;
    }

    public SchemaDef getSchema() {
        return schema;
    }

    public void setSchema(SchemaDef schema) {
        this.schema = schema;
    }

    public List<ForeignKeyRelation> getForeignKeyRelationList() {
        return foreignKeyRelationList;
    }

    public void setForeignKeyRelationList(List<ForeignKeyRelation> foreignKeyRelationList) {
        this.foreignKeyRelationList = foreignKeyRelationList;
    }
}
