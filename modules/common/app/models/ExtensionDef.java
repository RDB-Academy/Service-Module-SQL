package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author fabiomazzone
 */
@Entity
public class ExtensionDef extends BaseModel {
    @Id
    private Long id;

    @Constraints.Required
    @NotNull
    private String insertStatement;

    @ManyToOne(optional = false)
    private TableDef tableDef;

    public Long getId() {
        return id;
    }

    public String getInsertStatement() {
        return insertStatement;
    }

    public void setInsertStatement(String insertStatement) {
        this.insertStatement = insertStatement;
    }

    public TableDef getTableDef() {
        return tableDef;
    }

    public void setTableDef(TableDef tableDef) {
        this.tableDef = tableDef;
    }
}
