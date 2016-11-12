package models;

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
    private String name;

    @NotNull
    private String dataType;

    @NotNull
    private boolean isPrimary  = false;
    @NotNull
    private boolean isNullable = true;

    private int metaValueSet;

    private static final int META_VALUE_SET_FIRSTNAME = 8;
    private static final int META_VALUE_SET_LASTNAME = 744;


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

    public String getDatatype() {
        return dataType;
    }

    public void setDatatype(String datatype) {
        this.dataType = datatype;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }
}
