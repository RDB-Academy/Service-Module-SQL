package repository;

import com.google.inject.ImplementedBy;
import models.ForeignKeyRelation;

import java.util.List;

/**
 * @author Fabio Mazzone
 */
@ImplementedBy(ForeignKeyRelationRepositoryEbean.class)
public interface ForeignKeyRelationRepository {
    List<ForeignKeyRelation> getAll();

    ForeignKeyRelation getById(Long id);

    void save(ForeignKeyRelation foreignKeyRelation);
}

