package initializers;

import models.sqlTrainerService.*;

import java.util.List;

/**
 * @author fabiomazzone
 */
public abstract class SchemaBuilder {
    private SchemaDef schemaDef;

    protected abstract String getSchemaName();

    protected abstract SchemaDef buildSchema();

    protected abstract List<Task> buildTasks();

    public SchemaDef getSchemaDef() {
        if(schemaDef == null) {
            this.schemaDef = this.buildSchema();
            this.schemaDef.addTask(buildTasks());
        }
        return schemaDef;
    }

    boolean schemaExist(List<SchemaDef> schemaDefList) {
        SchemaDef schemaDef = schemaDefList.parallelStream()
                .filter(x -> x.getName().equals(getSchemaName()))
                .findAny()
                .orElse(null);

        return schemaDef != null;
    }

    protected TableDef createNewTableDef(String name) {
        TableDef tableDef = new TableDef();
        tableDef.setName(name);

        return tableDef;
    }

    protected SchemaDef createNewSchemaDef() {
        SchemaDef schemaDef = new SchemaDef();
        schemaDef.setName(this.getSchemaName());

        return schemaDef;
    }

    protected ColumnDef createNewColumnDef(String name, String dataType) {
        ColumnDef columnDef = new ColumnDef();
        columnDef.setName(name);
        columnDef.setDataType(dataType);

        return columnDef;
    }

    protected ForeignKey createForeignKey(String name) {
        ForeignKey foreignKey = new ForeignKey();
        foreignKey.setName(name);

        return foreignKey;
    }

    protected ForeignKeyRelation createForeignKeyRelation(ColumnDef sourceColumn, ColumnDef targetColumn) {
        ForeignKeyRelation foreignKeyRelation = new ForeignKeyRelation();

        foreignKeyRelation.setSourceColumn(sourceColumn);
        foreignKeyRelation.setTargetColumn(targetColumn);

        return foreignKeyRelation;
    }
}
