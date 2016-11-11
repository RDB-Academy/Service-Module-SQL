package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import models.ForeignKeyRelation;
import repository.implementation.ForeignKeyRelationRepositoryImplementation;

import java.util.Random;

/**
 * @author fabiomazzone
 */
@ImplementedBy(ForeignKeyRelationRepositoryImplementation.class)
public abstract class ForeignKeyRelationRepository implements Repository<ForeignKeyRelation> {
    protected static Model.Finder<Long, ForeignKeyRelation> find = new Model.Finder<Long, ForeignKeyRelation>(ForeignKeyRelation.class);

    public static ForeignKeyRelation getRandom() {
        return find.all().get((new Random()).nextInt(find.all().size()));
    }

    public void save(ForeignKeyRelation foreignKeyRelation) {
        foreignKeyRelation.save();
    }
}
