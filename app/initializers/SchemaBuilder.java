package initializers;

import models.*;

import java.util.List;

/**
 * @author fabiomazzone
 */
public abstract class SchemaBuilder {
    public abstract String getSchemaName();

    public abstract SchemaDef buildSchema();

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

    protected SchemaDef createNewSchemaDef(String name) {
        SchemaDef schemaDef = new SchemaDef();
        schemaDef.setName(name);

        return schemaDef;
    }

    protected ColumnDef createNewColumnDef(String name, String dataType) {
        ColumnDef columnDef = new ColumnDef();
        columnDef.setName(name);
        columnDef.setDatatype(dataType);

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
