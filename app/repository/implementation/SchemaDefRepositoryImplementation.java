package repository.implementation;

import com.google.inject.Singleton;
import models.SchemaDef;
import repository.SchemaDefRepository;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
@Singleton
public class SchemaDefRepositoryImplementation extends SchemaDefRepository {
    @Override
    public List<SchemaDef> getAll() {
        return this.find.all();
    }

    @Override
    public SchemaDef getById(Long id) {
        return this.find.byId(id);
    }
}
