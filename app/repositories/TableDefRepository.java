package repositories;

import com.google.inject.ImplementedBy;
import models.TableDef;

import java.util.List;
import java.util.Map;

/**
 * @author fabiomazzone
 */
@ImplementedBy(TableDefRepositoryEbean.class)
public interface TableDefRepository
{
    List<TableDef> getAll();
    TableDef getById(Long id);
    List<TableDef> getMatching(Map<String, List<String>> parameters);

    void save(TableDef tableDef);

    void delete(TableDef tableDef);
}

