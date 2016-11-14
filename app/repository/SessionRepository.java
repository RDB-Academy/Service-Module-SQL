package repository;

import com.google.inject.ImplementedBy;
import models.Session;
import repository.implementation.SessionRepositoryImplementation;

/**
 * @author fabiomazzone
 */
@ImplementedBy(SessionRepositoryImplementation.class)
public interface SessionRepository {
    Session getById(String sessionId);
}
