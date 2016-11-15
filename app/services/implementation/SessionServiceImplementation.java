package services.implementation;

import forms.LoginForm;
import models.Session;
import play.Logger;
import play.mvc.Http;
import repository.SessionRepository;
import services.SessionService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class SessionServiceImplementation implements SessionService {
    private SessionRepository sessionRepository;

    @Inject
    public SessionServiceImplementation(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void setSession(LoginForm loginForm, Http.Context ctx) {
        Session session = new Session();
        session.setUserName("admin");
        session.save();

        ctx.session().put(SESSION_FIELD_NAME, session.getId());

        Logger.info("SessionID" + session.getId());
    }

    @Override
    public Session getSession(Http.Context ctx) {
        if(ctx.session().isDirty) {
            return null;
        }

        String sessionId = ctx.session().get(SESSION_FIELD_NAME);

        return (sessionId != null && !sessionId.isEmpty()) ? sessionRepository.getById(sessionId) : null;
    }

    @Override
    public void clear(Http.Context ctx) {
        ctx.session().clear();
    }
}