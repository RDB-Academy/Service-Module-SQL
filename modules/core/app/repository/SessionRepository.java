package repository;

import com.google.inject.ImplementedBy;
import models.Session;

import javax.validation.constraints.NotNull;

/**
 * Interfaces for Session database access
 *
 * @author Fabio Mazzone
 */
@ImplementedBy(SessionRepositoryEbean.class)
public interface SessionRepository {
    /**
     * select session entity by id
     * @param sessionId a session Id
     * @return returns a session object or null
     */
    Session getById(@NotNull String sessionId);

    void save(Session session);
}

