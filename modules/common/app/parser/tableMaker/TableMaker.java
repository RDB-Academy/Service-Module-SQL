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

    public List<String> buildStatements() {
        List<String> createStatement = new ArrayList<>();
        List<String> primaryKeys = new ArrayList<>();

        for(TableDef tableDefs : schemaDef.getTableDefList()){
            String createString = "CREATE TABLE"
                    + tableDefs.getName()
                    + " (";

            for(ColumnDef columnDef : tableDefs.getColumnDefList()) {
                String column = " "
                        + columnDef.getName()
                        + columnDef.getDataType()
                        + ((columnDef.isNullable()) ? "" : " NOT NULL");

                createString =
                        createString + column
                                + ((tableDefs.getColumnDefList().indexOf(columnDef)) < tableDefs.getColumnDefList().size() - 1 ? "," : "");

                if (columnDef.isPrimary()) {
                    primaryKeys.add(columnDef.getName());
                }
            }

            if (primaryKeys.size() >  0){
                createString = createString + ", PRIMARY KEY (";
                for (String addPK: primaryKeys){
                    createString = createString
                            + addPK
                            + ((primaryKeys.indexOf(addPK)) < primaryKeys.size() ? ", " : "");
                }
                createString = createString + ")";
            }

            createString = createString + ");";
            createStatement.add(createString);
        }

        createStatement.forEach(System.out::println);

        return createStatement;

    }

    }

