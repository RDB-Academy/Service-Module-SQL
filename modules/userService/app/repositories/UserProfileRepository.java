package repositories;

import models.UserProfile;

import javax.validation.constraints.NotNull;

/**
 * @author fabiomazzone
 * @version 1.0, 06.07.17
 */

public interface UserProfileRepository {
    UserProfile getById(@NotNull Long id);

    void save(UserProfile userProfile);
    void delete(UserProfile userProfile);
}
