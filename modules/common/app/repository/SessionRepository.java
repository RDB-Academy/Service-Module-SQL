package repository;

import com.avaje.ebean.Model;
import models.Session;

import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class SessionRepository {
    private Model.Finder<String, Session> find = new Model.Finder<>(Session.class);

    public Session getById(String sessionId) {
        return find.byId(sessionId);
    }
}
