package models;

import javax.persistence.*;

/**
 * @author invisible
 */
@Entity
public class ForeignKeyRelation extends BaseModel {
    @Id
    private Long id;

    @ManyToOne(optional = false)
    private ForeignKey foreignKey;

    @ManyToOne(optional = false)
    private ColumnDef sourceColumn;

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
}
