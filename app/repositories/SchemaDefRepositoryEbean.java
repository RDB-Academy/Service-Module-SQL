package repositories;

import com.google.inject.Singleton;
import io.ebean.Finder;
import models.SchemaDef;

import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefRepositoryEbean implements SchemaDefRepository
{
    private Finder<Long, SchemaDef> find = new Finder<>(SchemaDef.class);

    public List<SchemaDef> getAll()
    {
        return this.find.all();
    }

    public SchemaDef getById(Long id)
    {
        return this.find.byId(id);
    }

    public SchemaDef getByName(String name)
    {
        return this.find.query().where().eq("name", name).findUnique();
    }

    public void save(SchemaDef schemaDef)
    {
        find.db().save(schemaDef);
    }

    @Override
    public void delete(SchemaDef schemaDef)
    {
        this.find.db().delete(schemaDef);
    }
}
