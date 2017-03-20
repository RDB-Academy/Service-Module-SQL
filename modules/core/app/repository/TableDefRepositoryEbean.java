package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;
import models.TableDef;

import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class TableDefRepositoryEbean implements TableDefRepository{
    private Model.Finder<Long, TableDef> find = new Model.Finder<>(TableDef.class);

    public List<TableDef> getAll() {
        return this.find.all();
    }

    public TableDef getById(Long id) {
        return this.find.byId(id);
    }

    public void save(TableDef tableDef) {
        tableDef.save();
    }
}
