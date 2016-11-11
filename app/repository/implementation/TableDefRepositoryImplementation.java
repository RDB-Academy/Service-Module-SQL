package repository.implementation;

import com.google.inject.Singleton;
import models.TableDef;
import repository.TableDefRepository;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
@Singleton
public class TableDefRepositoryImplementation extends TableDefRepository {
    @Override
    public List<TableDef> getAll() {
        return this.find.all();
    }

    @Override
    public TableDef getById(long id) {
        return this.find.byId(id);
    }
}
