package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;
import models.SchemaDef;

import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefRepository {
    private Model.Finder<Long, SchemaDef> find = new Model.Finder<>(SchemaDef.class);

    public List<SchemaDef> getAll() {
        return this.find.all();
    }

    public SchemaDef getById(Long id) {
        return this.find.byId(id);
    }

    public SchemaDef getByName(String name) {
        return this.find.where().eq("name", name).findUnique();
    }

    public void save(SchemaDef schemaDef) {
        schemaDef.save();
    }
}
