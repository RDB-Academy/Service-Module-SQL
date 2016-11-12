package extensionMaker;

import models.SchemaDef;
import models.TableDef;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * Created by carl on 12.11.16.
 */
public class ExtensionMaker {
    Long seed;
    Random rand;
    public ExtensionMaker(Long seed) {
        this.seed = seed;
        this.rand = new Random(this.seed);
    }
    public int test(SchemaDef schemaDef) {
        schemaDef.getTableDefList().forEach(System.out::println);
        List<TableDef> tableDefs = schemaDef.getTableDefList();
        System.out.println(tableDefs.get(0).getName());
        System.out.println(tableDefs.get(0).getColumnDefList().get(0).getName());
        System.out.println(tableDefs.get(0).getColumnDefList().get(1).getName());
        return 1;
    }
}
