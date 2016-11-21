package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Entity
public class ColumnDef extends BaseModel {
    @Id
    private Long id;

    @ManyToOne(optional = false)
    private TableDef tableDef;

    @NotNull
    @Constraints.Required
    private String name;

    @NotNull
    @Constraints.Required
    private String dataType;

    @NotNull
    private boolean isPrimary  = false;
    @NotNull
    private boolean isNullable = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceColumn")
    private List<ForeignKeyRelation> foreignKeyRelationsSource;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetColumn")
    private List<ForeignKeyRelation> foreignKeyRelationsTarget;


    private int metaValueSet;

    public static final int META_VALUE_SET_FIRSTNAME = 8;
    public static final int META_VALUE_SET_LASTNAME = 744;


    public long getId() {
        return id;
    }

    public TableDef getTableDef() {
        return tableDef;
    }

    public void setTableDef(TableDef tableDef) {
        this.tableDef = tableDef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        if(primary) {
            this.setNullable(false);
        }
        isPrimary = primary;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public List<ForeignKeyRelation> getForeignKeyRelationsSource() {
        return foreignKeyRelationsSource;
    }

    public List<ForeignKeyRelation> getForeignKeyRelationsTarget() {
        return foreignKeyRelationsTarget;
    }

    public int getMetaValueSet() {
        return metaValueSet;
    }

    public void setMetaValueSet(int metaValueSet) {
        this.metaValueSet = metaValueSet;
    }
}
