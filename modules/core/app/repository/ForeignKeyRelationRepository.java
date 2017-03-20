package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;
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

