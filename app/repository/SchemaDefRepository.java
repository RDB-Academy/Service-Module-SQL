package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import models.SchemaDef;
import repository.implementation.SchemaDefRepositoryImplementation;

/**
 * @author fabiomazzone
 */
@ImplementedBy(SchemaDefRepositoryImplementation.class)
public abstract class SchemaDefRepository implements Repository<SchemaDef> {
    protected Model.Finder<Long, SchemaDef> find = new Model.Finder<>(SchemaDef.class);

    public void save(SchemaDef schemaDef) {
        schemaDef.save();
    }
}
