package repositories;

import com.google.inject.ImplementedBy;
import models.UserProfile;

import java.util.List;

@ImplementedBy(UserProfileRepositoryEbean.class)
public interface UserProfileRepository {
    List<UserProfile> getAll();

    UserProfile getById(Long id);

    void save(UserProfile userProfile);
    void delete(UserProfile userProfile);
}

