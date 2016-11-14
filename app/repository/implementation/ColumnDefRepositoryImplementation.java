package repository.implementation;

import com.google.inject.Singleton;
import models.ColumnDef;
import repository.ColumnDefRepository;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
@Singleton
public class ColumnDefRepositoryImplementation extends ColumnDefRepository {
    @Override
    public List<ColumnDef> getAll() {
        return this.find.all();
    }

    @Override
    public ColumnDef getById(Long id) {
        return this.find.byId(id);
    }
}
