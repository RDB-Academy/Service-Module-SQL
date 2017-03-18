package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;
import models.ForeignKeyRelation;

import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class ForeignKeyRelationRepository {
    private Model.Finder<Long, ForeignKeyRelation> find = new Model.Finder<>(ForeignKeyRelation.class);

    public List<ForeignKeyRelation> getAll() {
        return this.find.all();
    }


    public ForeignKeyRelation getById(Long id) {
        return this.find.byId(id);
    }
}
