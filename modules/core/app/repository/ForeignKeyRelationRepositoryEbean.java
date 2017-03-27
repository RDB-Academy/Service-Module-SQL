package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;
import models.ForeignKeyRelation;

import java.util.List;

/**
 * @author Fabio Mazzone
 */
@Singleton
public class ForeignKeyRelationRepositoryEbean implements ForeignKeyRelationRepository
{
    private Model.Finder<Long, ForeignKeyRelation> find = new Model.Finder<>(ForeignKeyRelation.class);

    public List<ForeignKeyRelation> getAll()
    {
        return this.find.all();
    }


    public ForeignKeyRelation getById(Long id)
    {
        return this.find.byId(id);
    }

    @Override
    public void save(ForeignKeyRelation foreignKeyRelation)
    {
        this.find.db().save(foreignKeyRelation);
    }

    @Override
    public void delete(ForeignKeyRelation foreignKeyRelation)
    {
        this.find.db().delete(foreignKeyRelation);
    }
}
