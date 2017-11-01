package repositories;

import com.google.inject.Singleton;
import io.ebean.Finder;
import models.UserProfile;

import java.util.List;

@Singleton
public class UserProfileRepositoryEbean implements UserProfileRepository {
    private Finder<Long, UserProfile> find = new Finder<>(UserProfile.class);

    @Override
    public List<UserProfile> getAll() {
        return find.all();
    }

    @Override
    public UserProfile getById(Long id) {
        return find.byId(id);
    }

    @Override
    public void save(UserProfile userProfile) {
        this.find.db().save(userProfile);
    }

    @Override
    public void delete(UserProfile userProfile) {
        find.db().delete(userProfile);
    }
}
