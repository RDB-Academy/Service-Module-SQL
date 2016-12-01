package parser.tableMaker;

import models.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author fabiomazzone
 */
public class TableMaker {
    private final SchemaDef schemaDef;

    public TableMaker(SchemaDef schemaDef) {
        this.schemaDef = schemaDef;
    }

    public List<String> buildStatement() {
        List<String> createStatement = new ArrayList<>();

        List<ForeignKey> foreignKeyList = schemaDef.getForeignKeyList();
        for(ForeignKey foreignKey : foreignKeyList) {
            List<String> sourceCol = new ArrayList<>();
            List<String> targetCol = new ArrayList<>();
            String sourceTable = "";
            String targetTable = "";
            if (foreignKey.getForeignKeyRelationList().size() > 0) {
                for (ForeignKeyRelation foreignKeyRelation : foreignKey.getForeignKeyRelationList()) {
                    sourceTable = foreignKeyRelation.getSourceColumn().getTableDef().getName();
                    sourceCol.add(foreignKeyRelation.getSourceColumn().getName());
                    targetTable = foreignKeyRelation.getTargetColumn().getTableDef().getName();
                    targetCol.add(foreignKeyRelation.getTargetColumn().getName());
                }
            }
            String alterTable = "ALTER TABLE " + sourceTable + " ADD CONSTRAINT " + foreignKey.getName() +
                    " FOREIGN KEY (" + String.join("," , sourceCol) + ") REFERENCES " + targetTable + "("
                    + String.join("," , targetCol) + ")";
            System.out.println(alterTable);

        }

        for(TableDef tableDef : schemaDef.getTableDefList()){
            String createString = breakDownTableDef(tableDef);
            createStatement.add(createString);
        }

        createStatement.forEach(System.out::println);
        foreignKeyList.forEach(System.out::println);


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
                + ((columnDef.isNullable()) ? "" : " NOT NULL");

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

    }

