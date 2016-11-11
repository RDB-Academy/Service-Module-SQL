package models;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by invisible on 11/10/16.
 */
public class ForeignKeyRelation {
    @Id
    long id;

    @ManyToOne
    ForeignKey foreignKey;

    @ManyToOne
    ColumnDef sourceColumn;

    @ManyToOne
    ColumnDef targetColumn;


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
