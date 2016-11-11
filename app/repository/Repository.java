package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
public interface Repository<T extends Model> {
    public List<T> getAll();

    public T getById(long id);

    public void save(T model);
}
