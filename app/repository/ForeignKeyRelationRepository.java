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
    protected Model.Finder<Long, ForeignKeyRelation> find = new Model.Finder<Long, ForeignKeyRelation>(ForeignKeyRelation.class);

    public void save(ForeignKeyRelation foreignKeyRelation) {
        foreignKeyRelation.save();
    }
}
