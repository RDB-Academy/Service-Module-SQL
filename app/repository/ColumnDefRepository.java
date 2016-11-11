package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import models.ColumnDef;
import repository.implementation.ColumnDefRepositoryImplementation;

import java.util.Random;

/**
 * @author fabiomazzone
 */
@ImplementedBy(ColumnDefRepositoryImplementation.class)
public abstract class ColumnDefRepository implements Repository<ColumnDef> {
    protected static Model.Finder<Long, ColumnDef> find = new Model.Finder<Long, ColumnDef>(ColumnDef.class);

    public static ColumnDef getRandom() {
        return find.all().get((new Random()).nextInt(find.all().size()));
    }

    public void save(ColumnDef columnDef) {
        columnDef.save();
    }
}
