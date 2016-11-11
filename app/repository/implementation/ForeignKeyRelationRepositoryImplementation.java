package repository.implementation;

import com.google.inject.Singleton;
import models.ForeignKeyRelation;
import repository.ForeignKeyRelationRepository;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
@Singleton
public class ForeignKeyRelationRepositoryImplementation extends ForeignKeyRelationRepository {
    @Override
    public List<ForeignKeyRelation> getAll() {
        return this.find.all();
    }

    @Override
    public ForeignKeyRelation getById(long id) {
        return this.find.byId(id);
    }
}
