package extensionMaker;

import models.SchemaDef;
import models.TableDef;

import java.util.List;
import java.util.Random;

/**
 * Created by carl on 12.11.16.
 */
public class ExtensionMaker {
    private Long seed;
    private Random rand;

    public ExtensionMaker(Long seed) {
        this.seed = seed;
        this.rand = new Random(this.seed);
    }


    public String[][] buildStatements(SchemaDef schemaDef) {
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
