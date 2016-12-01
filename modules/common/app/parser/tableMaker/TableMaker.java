package parser.tableMaker;

import models.ColumnDef;
import models.SchemaDef;
import models.TableDef;

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

        for(TableDef tableDef : schemaDef.getTableDefList()){
            String createString = breakDownTableDef(tableDef);
            createStatement.add(createString);
        }

        createStatement.forEach(System.out::println);

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
                        + ((tableDef.getColumnDefList().indexOf(columnDef)) < tableDef.getColumnDefList().size() -1 ? "," : "");

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
                    + ((primaryKeys.indexOf(addPK)) > primaryKeys.size() ? ", " : "");
        }
        create = create + ")";

        return create;
    }

    }

