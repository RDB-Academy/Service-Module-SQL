package repository;

import com.avaje.ebean.Model;
import models.SchemaDef;

/**
 * @author fabiomazzone
 */
public class SchemaDefRepository {
    private static Model.Finder<Long, SchemaDef> find = new Model.Finder<>(SchemaDef.class);

    public static SchemaDef getById(long id) {
        return find.byId(id);
    }
}
