package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;
import models.ColumnDef;

import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class ColumnDefRepository {
    private Model.Finder<Long, ColumnDef> find = new Model.Finder<>(ColumnDef.class);
    public List<ColumnDef> getAll() {
        return this.find.all();
    }

    public ColumnDef getById(Long id) {
        return this.find.byId(id);
    }
}
