package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;
import models.TableDef;

import java.util.List;

/**
 * @author fabiomazzone
 */
@ImplementedBy(TableDefRepositoryEbean.class)
public interface TableDefRepository {
    List<TableDef> getAll();
    TableDef getById(Long id);

    void save(TableDef tableDef);
}

