package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceColumn")
    private List<ForeignKeyRelation> foreignKeyRelationsSource;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetColumn")
    private List<ForeignKeyRelation> foreignKeyRelationsTarget;


    @Column(nullable = false)
    private int metaValueSet;

    public static final int META_VALUE_SET_ID = 485;
    public static final int META_VALUE_SET_FOREIGN_KEY = 358;

    public static final int META_VALUE_SET_NAME = 768;
    public static final int META_VALUE_SET_FIRSTNAME = 8;
    public static final int META_VALUE_SET_LASTNAME = 744;
    public static final int META_VALUE_SET_FULLNAME = 5;

    public static final int META_VALUE_SET_CITY = 3;
    public static final int META_VALUE_SET_TITLE = 4;
    public static final int META_VALUE_SET_DAY = 7;
    public static final int META_VALUE_SET_MONTH = 9;
    public static final int META_VALUE_SET_MAIL = 1;
    public static final int META_VALUE_SET_ANIMAL = 2;
    public static final int META_VALUE_SET_METAL = 6;
    public static final int META_VALUE_SET_COLOUR = 10;
    public static final int META_VALUE_SET_DATE = 11;




    public static final int META_VALUE_SET_GRADE = 361;

    public static final int META_VALUE_SET_LOCATION = 952;

    public static final int META_VALUE_SET_YEAR = 154;

    public static final int META_VALUE_SET_LOREM_IPSUM = 672;

    public Long getId() {
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
