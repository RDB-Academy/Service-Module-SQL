package repositories.sqlTrainerService;

import com.google.inject.Singleton;
import io.ebean.Finder;
import models.sqlTrainerService.TableDef;
import repositories.QueryProvider;

import java.util.List;
import java.util.Map;

/**
 * @author fabiomazzone
 */
@Singleton
public class TableDefRepositoryEbean implements TableDefRepository
{
    private Finder<Long, TableDef> find = new Finder<>(TableDef.class);
    private QueryProvider<TableDef> queryProvider = new QueryProvider<>(this.find);

    public List<TableDef> getAll()
    {
        return this.find.all();
    }

    public TableDef getById(Long id)
    {
        return this.find.byId(id);
    }

    /**
     *
     * @param parameters Map matching the design for QueryProvider.filterOnParameters
     * @return
     */
    public List<TableDef> getMatching(Map<String, List<String>> parameters) {
        return this.queryProvider.filterOnParameters(parameters).findList();
    }

    public void save(TableDef tableDef)
    {
        this.find.db().save(tableDef);
    }

    public void delete(TableDef tableDef)
    {
        this.find.db().delete(tableDef);
    }
}

