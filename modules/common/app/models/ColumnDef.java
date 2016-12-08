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
    private boolean isNotNull = false;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceColumn")
    private List<ForeignKeyRelation> foreignKeyRelationsSource;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetColumn")
    private List<ForeignKeyRelation> foreignKeyRelationsTarget;

    @JsonIgnore
    @Column(nullable = false)
    private int metaValueSet;

    @JsonIgnore
    private int minValueSet;

    @JsonIgnore
    private int maxValueSet;

    public static final int META_VALUE_SET_ID = 485;
    public static final int META_VALUE_SET_FOREIGN_KEY = 358;

    public static final int MIN_VALUE_SET = 398;
    public static final int MAX_VALUE_SET = 158;

    public static final int META_VALUE_SET_NAME = 768;
    public static final int META_VALUE_SET_FIRSTNAME = 8;
    public static final int META_VALUE_SET_LASTNAME = 744;
    public static final int META_VALUE_SET_FULLNAME = 5;

    public static final int META_VALUE_SET_MAIL = 1;

    public static final int META_VALUE_SET_TITLE = 4;
    public static final int META_VALUE_SET_ANIMAL = 2;
    public static final int META_VALUE_SET_METAL = 6;
    public static final int META_VALUE_SET_COLOR = 10;

    public static final int META_VALUE_SET_GRADE = 361;

    public static final int META_VALUE_SET_LOCATION = 952;
    public static final int META_VALUE_SET_CITY = 3;

    public static final int META_VALUE_SET_DAY = 7;
    public static final int META_VALUE_SET_MONTH = 9;
    public static final int META_VALUE_SET_YEAR = 154;
    public static final int META_VALUE_SET_DATE = 11;

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
            this.setNotNull(true);
        }
        isPrimary = primary;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
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

    public int getMinValueSet() {
        return minValueSet;
    }

    public void setMinValueSet(int minValueSet) {
        this.minValueSet = minValueSet;
    }

    public int getMaxValueSet() {
        return maxValueSet;
    }

    public void setMaxValueSet(int maxValueSet) {
        this.maxValueSet = maxValueSet;
    }

    public String getMetaValueSetName() {
        switch (this.getMetaValueSet()) {
            case META_VALUE_SET_ID:
                return "ID";
            case META_VALUE_SET_FOREIGN_KEY:
                return "FK_ID";

            case META_VALUE_SET_NAME:
                return "Name";
            case META_VALUE_SET_FIRSTNAME:
                return "First Name";
            case META_VALUE_SET_LASTNAME:
                return "Last Name";
            case META_VALUE_SET_FULLNAME:
                return "Full Name";

            case META_VALUE_SET_CITY:
                return "City";
            case META_VALUE_SET_TITLE:
                return "Title";
            case META_VALUE_SET_DAY:
                return "Day";
            case META_VALUE_SET_MONTH:
                return "Month";
            case META_VALUE_SET_MAIL:
                return "Mail";
            case META_VALUE_SET_ANIMAL:
                return "Animal";
            case META_VALUE_SET_METAL:
                return "Metal";
            case META_VALUE_SET_COLOR:
                return "Color";
            case META_VALUE_SET_DATE:
                return "Date";
            case META_VALUE_SET_GRADE:
                return "Grade";
            case META_VALUE_SET_LOCATION:
                return "Location";
            case META_VALUE_SET_YEAR:
                return "Year";
            case META_VALUE_SET_LOREM_IPSUM:
                return "Lorem Ipsum";
            default:
                return "NaN";
        }
    }
}
