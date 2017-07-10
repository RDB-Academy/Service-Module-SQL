package models.sqlTrainerService;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import models.BaseModel;

import javax.persistence.*;

/**
 * @author invisible
 */
@Entity
public class ForeignKeyRelation extends BaseModel {
    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    private ForeignKey foreignKey;

    @JsonIgnore
    @ManyToOne(optional = false)
    private ColumnDef sourceColumn;

    @JsonIgnore
    @ManyToOne(optional = false)
    private ColumnDef targetColumn;

    public Long getId() {
        return id;
    }

    public ForeignKey getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(ForeignKey foreignKey) {
        this.foreignKey = foreignKey;
    }

    public ColumnDef getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(ColumnDef sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public ColumnDef getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(ColumnDef targetColumn) {
        this.targetColumn = targetColumn;
    }

    @JsonGetter("foreignKey")
    public long getForeignKeyId() {
        return foreignKey.getId();
    }

    @JsonGetter("sourceColumn")
    public long getSourceColumnId() {
        return sourceColumn.getId();
    }

    @JsonGetter("targetColumn")
    public long getTargetColumnId() {
        return targetColumn.getId();
    }
}
