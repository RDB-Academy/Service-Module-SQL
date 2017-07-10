package repositories.sqlTrainerService;

import com.google.inject.Singleton;
import io.ebean.Finder;
import models.sqlTrainerService.ColumnDef;

import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class ColumnDefRepositoryEbean implements ColumnDefRepository
{
    private Finder<Long, ColumnDef> find = new Finder<>(ColumnDef.class);

    public List<ColumnDef> getAll()
    {
        return this.find.all();
    }

    public ColumnDef getById(Long id)
    {
        return this.find.byId(id);
    }

    public void save(ColumnDef columnDef)
    {
        this.find.db().save(columnDef);
    }

    @Override
    public void delete(ColumnDef columnDef)
    {
        this.find.db().delete(columnDef);
    }
}
