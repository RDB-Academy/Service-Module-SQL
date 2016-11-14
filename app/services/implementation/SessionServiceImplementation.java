package services.implementation;

import forms.LoginForm;
import models.Session;
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
        session.save();

        ctx.response().setHeader("X-AUTH", session.getId());
    }

    @Override
    public Session getSession(Http.Context ctx) {
        String sessionId = ctx.request().getHeader("X-AUTH");

        return (sessionId != null && !sessionId.isEmpty()) ? sessionRepository.getById(sessionId) : null;
    }
}