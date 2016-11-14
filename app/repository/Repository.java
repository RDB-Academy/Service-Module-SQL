package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
interface Repository<T extends Model> {
    List<T> getAll();

    T getById(Long id);

    void save(T model);
}
