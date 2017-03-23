package repository;

import com.google.inject.ImplementedBy;
import models.ForeignKey;

import java.util.List;

/**
 * @author Fabio Mazzone
 */
@ImplementedBy(ForeignKeyRepositoryEbean.class)
public interface ForeignKeyRepository {
    List<ForeignKey> getAll();

    ForeignKey getById(Long id);

    void save(ForeignKey foreignKey);
}

