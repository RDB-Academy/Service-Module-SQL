package repository.implementation;

import com.avaje.ebean.Model;
import models.Session;
import repository.SessionRepository;

import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class SessionRepositoryImplementation implements SessionRepository {
    private Model.Finder<String, Session> find = new Model.Finder<>(Session.class);

    @Override
    public Session getById(String sessionId) {
        return find.byId(sessionId);
    }
}
