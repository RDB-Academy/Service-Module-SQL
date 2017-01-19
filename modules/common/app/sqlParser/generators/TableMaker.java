package sqlParser.generators;

import models.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author gabrielahlers
 */
public class TableMaker {
    private final SchemaDef schemaDef;

    public TableMaker(SchemaDef schemaDef) {
        this.schemaDef = schemaDef;
    }

    public List<String> buildStatement() {
        List<String> createStatement = new ArrayList<>();

        List<ForeignKey> foreignKeyList = schemaDef.getForeignKeyList();


        for(TableDef tableDef : schemaDef.getTableDefList()){
            String createString = breakDownTableDef(tableDef);
            createStatement.add(createString);
        }

        if (!(foreignKeyList.isEmpty())) {
            List<String> fk = addForeignKeys(foreignKeyList);
            for (String add : fk) {
                createStatement.add(add);
            }
        }

        return createStatement;
    }

    private String breakDownTableDef(TableDef tableDef) {
        List<String> primaryKeys = new ArrayList<>();

        String create = "CREATE TABLE "
                + tableDef.getName()
                + "(";

        for (ColumnDef columnDef : tableDef.getColumnDefList()) {
            String column = breakDownColumnDef(columnDef);

            create = create + column
                        + ((tableDef.getColumnDefList().indexOf(columnDef)) < tableDef.getColumnDefList().size() - 1 ? "," : "");

            if (columnDef.isPrimary()) {
                primaryKeys.add(columnDef.getName());
            }
        }

        if (primaryKeys.size() > 0){
            create = create + addPrimaryKeys(primaryKeys);
        }

        create = create + ");";

        return create;
    }

    private String breakDownColumnDef(ColumnDef columnDef) {
        String create = " "
                + columnDef.getName() + " "
                + columnDef.getDataType()
                + ((columnDef.isNotNull()) ? " NOT NULL" : " ");

        return create;
    }

    private String addPrimaryKeys(List<String> primaryKeys) {
        String create = ", PRIMARY KEY (";

        for (String addPK: primaryKeys) {
            create = create
                    + addPK
                    + ((primaryKeys.indexOf(addPK)) < primaryKeys.size() - 1 ? ", " : "");
        }
        create = create + ")";

        return create;
    }

    private List<String> addForeignKeys(List<ForeignKey> foreignKeyList) {
        List<String> foreignKeys = new ArrayList<>();
        for (ForeignKey foreignKey : foreignKeyList) {
            String sourceTable = "";
            String targetTable = "";
            List<String> sourceCol = new ArrayList<>();
            List<String> targetCol = new ArrayList<>();
            for (ForeignKeyRelation foreignKeyRelation : foreignKey.getForeignKeyRelationList()) {
                targetTable = foreignKeyRelation.getTargetColumn().getTableDef().getName();
                sourceTable = foreignKeyRelation.getSourceColumn().getTableDef().getName();
                sourceCol.add(foreignKeyRelation.getSourceColumn().getName());
                targetCol.add(foreignKeyRelation.getTargetColumn().getName());
            }
            String alterTable = "ALTER TABLE " + sourceTable + " ADD CONSTRAINT " + foreignKey.getName() +
                         " FOREIGN KEY (" + String.join("," , sourceCol) + ") REFERENCES " + targetTable + "("
                         + String.join("," , targetCol) + ");";
            foreignKeys.add(alterTable);
        }

        return foreignKeys;
    }

    }

