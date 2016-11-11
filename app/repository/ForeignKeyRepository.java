package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import models.ForeignKey;
import repository.implementation.ForeignKeyRepositoryImplementation;

import java.util.Random;

/**
 * @author fabiomazzone
 */
@ImplementedBy(ForeignKeyRepositoryImplementation.class)
public abstract class ForeignKeyRepository implements Repository<ForeignKey> {
    protected static Model.Finder<Long, ForeignKey> find = new Model.Finder<Long, ForeignKey>(ForeignKey.class);

    public static ForeignKey getRandom() {
        return find.all().get((new Random()).nextInt(find.all().size()));
    }

    public void save(ForeignKey foreignKey) {
        foreignKey.save();
    }
}
