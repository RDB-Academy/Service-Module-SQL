package repositories;

import io.ebean.Finder;
import models.Session;

import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class SessionRepositoryEbean implements SessionRepository
{
    private Finder<String, Session> find = new Finder<>(Session.class);

    public Session getById(String sessionId)
    {
        return find.byId(sessionId);
    }

    @Override
    public void save(Session session)
    {
        find.db().save(session);
    }

    @Override
    public void delete(Session session)
    {
        this.find.db().delete(session);
    }
}
