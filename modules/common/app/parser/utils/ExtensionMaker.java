package parser.utils;

import models.SchemaDef;
import models.TableDef;

import java.util.List;
import java.util.Random;

/**
 * @author carl
 */
public class ExtensionMaker {
    private final Long      seed;
    private final SchemaDef schemaDef;
    private final Random    rand;

    public ExtensionMaker(Long seed, SchemaDef schemaDef) {
        this.seed = seed;
        this.schemaDef = schemaDef;
        this.rand = new Random(this.seed);
    }


    public String[][] buildStatements() {
        String[][] insertStatements = new String[20][20];

        for(int i = 0; i < insertStatements.length; i++) {
            for(int j = 0; j < insertStatements[i].length; j++) {
                insertStatements[i][j] = "" + (i + j) + "";
            }
        }

        schemaDef.getTableDefList().forEach(System.out::println);
        List<TableDef> tableDefs = schemaDef.getTableDefList();
        System.out.println(tableDefs.get(0).getName());
        System.out.println(tableDefs.get(0).getColumnDefList().get(0).getName());
        System.out.println(tableDefs.get(0).getColumnDefList().get(1).getName());

        return insertStatements;
    }
}
