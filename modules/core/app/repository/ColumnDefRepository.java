package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;
import models.ColumnDef;

import java.util.List;

@ImplementedBy(ColumnDefRepositoryEbean.class)
public interface ColumnDefRepository {
    List<ColumnDef> getAll();

    ColumnDef getById(Long id);

    void save(ColumnDef columnDef);
}

