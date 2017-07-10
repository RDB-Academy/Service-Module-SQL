package repositories.sqlTrainerService;

import com.google.inject.ImplementedBy;
import models.sqlTrainerService.ForeignKeyRelation;

import java.util.List;

/**
 * @author Fabio Mazzone
 */
@ImplementedBy(ForeignKeyRelationRepositoryEbean.class)
public interface ForeignKeyRelationRepository
{
    List<ForeignKeyRelation> getAll();
    ForeignKeyRelation getById(Long id);

    void save(ForeignKeyRelation foreignKeyRelation);
    void delete(ForeignKeyRelation foreignKeyRelation);
}

