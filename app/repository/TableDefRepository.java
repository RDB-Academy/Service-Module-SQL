package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import models.TableDef;
import repository.implementation.TableDefRepositoryImplementation;

import java.util.Random;

/**
 * @author fabiomazzone
 */
@ImplementedBy(TableDefRepositoryImplementation.class)
public abstract class TableDefRepository implements Repository<TableDef> {
    protected static Model.Finder<Long, TableDef> find = new Model.Finder<Long, TableDef>(TableDef.class);

    public static TableDef getRandom() {
        return find.all().get((new Random()).nextInt(find.all().size()));
    }

    public void save(TableDef tableDef) {
        tableDef.save();
    }
}
