package repositories.sqlTrainerService;

import com.google.inject.Singleton;
import io.ebean.Finder;
import models.sqlTrainerService.ForeignKey;

import java.util.List;

/**
 * @author Fabio Mazzone
 */
@Singleton
public class ForeignKeyRepositoryEbean implements ForeignKeyRepository
{
    private Finder<Long, ForeignKey> find = new Finder<>(ForeignKey.class);

    public List<ForeignKey> getAll()
    {
        return this.find.all();
    }

    public ForeignKey getById(Long id)
    {
        return this.find.byId(id);
    }

    @Override
    public void save(ForeignKey foreignKey)
    {
        this.find.db().save(foreignKey);
    }

    @Override
    public void delete(ForeignKey foreignKey)
    {
        this.find.db().delete(foreignKey);
    }


}
