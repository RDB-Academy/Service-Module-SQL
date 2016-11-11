package repository.implementation;

import com.google.inject.Singleton;
import models.ForeignKey;
import repository.ForeignKeyRepository;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
@Singleton
public class ForeignKeyRepositoryImplementation extends ForeignKeyRepository {
    @Override
    public List<ForeignKey> getAll() {
        return this.find.all();
    }

    @Override
    public ForeignKey getById(long id) {
        return this.find.byId(id);
    }
}
