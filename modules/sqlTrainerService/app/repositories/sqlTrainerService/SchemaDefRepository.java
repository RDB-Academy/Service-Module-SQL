package repositories.sqlTrainerService;

import com.google.inject.ImplementedBy;
import models.sqlTrainerService.SchemaDef;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Fabio Mazzone
 */
@ImplementedBy(SchemaDefRepositoryEbean.class)
public interface SchemaDefRepository
{
    List<SchemaDef> getAll();
    SchemaDef getById(@NotNull Long id);
    SchemaDef getByName(@NotNull String name);
    void save(@NotNull SchemaDef schemaDef);
    void delete(SchemaDef schemaDef);
}

