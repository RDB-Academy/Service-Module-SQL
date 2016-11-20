package models;

import javax.persistence.*;

/**
 * @author invisible
 */
@Entity
public class ForeignKeyRelation extends BaseModel {
    @Id
    private Long id;

    @ManyToOne
    private ForeignKey foreignKey;

    @ManyToOne
    private ColumnDef sourceColumn;

    @ManyToOne
    private ColumnDef targetColumn;


    public long getId() {
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
