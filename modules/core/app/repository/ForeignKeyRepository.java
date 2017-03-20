package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;
import models.ForeignKey;

import java.util.List;

/**
 * @author Fabio Mazzone
 */
@ImplementedBy(ForeignKeyRepository.class)
public interface ForeignKeyRepository {
    List<ForeignKey> getAll();

    ForeignKey getById(Long id);

    void save(ForeignKey foreignKey);
}

