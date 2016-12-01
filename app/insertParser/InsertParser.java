package insertParser;

import models.SchemaDef;
import models.TableDef;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;
/**
 * Created by carl on 17.11.16.
 */
public class InsertParser {

    private Long seed;
    private Random rand;

    public InsertParser(Long seed) {
        this.seed = seed;
        this.rand = new Random(this.seed);
    }

    public String parseStatement(String[][] Extension) {

        String name = "NAME";

        int row = Extension.length;
        int col = Extension[0].length;

        for(int i = 0; i < row; i++) {
            for(int j = 0; i < col; j++) {
                
            }
        }


        String insertStatements = "kahn";



        return insertStatements;
    }

}
