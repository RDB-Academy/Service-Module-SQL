package repositories.sqlTrainerService;

import com.google.inject.ImplementedBy;
import models.sqlTrainerService.ColumnDef;

import java.util.List;

@ImplementedBy(ColumnDefRepositoryEbean.class)
public interface ColumnDefRepository
{
    List<ColumnDef> getAll();

    ColumnDef getById(Long id);

    void save(ColumnDef columnDef);
    void delete(ColumnDef columnDef);
}

