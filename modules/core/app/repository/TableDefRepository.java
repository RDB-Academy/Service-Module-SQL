package repository;

import com.google.inject.ImplementedBy;
import models.TableDef;

import java.util.List;

/**
 * @author fabiomazzone
 */
@ImplementedBy(TableDefRepositoryEbean.class)
public interface TableDefRepository
{
    List<TableDef> getAll();
    TableDef getById(Long id);

    void save(TableDef tableDef);

    void delete(TableDef tableDef);
}

