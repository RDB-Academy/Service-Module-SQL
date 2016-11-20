package services;

import eu.bitwalker.useragentutils.UserAgent;
import forms.LoginForm;
import models.Session;
import play.mvc.Http;
import repository.SessionRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class SessionService {
    private static final String SESSION_FIELD_NAME = "SessionID";
    private SessionRepository sessionRepository;

    @Inject
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void setSession(LoginForm loginForm, Http.Context ctx) {
        Session session = new Session();
        session.setUserName("admin");

        UserAgent userAgent = new UserAgent(ctx.request().getHeader(Http.HeaderNames.USER_AGENT));
        String connectedData = userAgent.toString() + ctx.request().remoteAddress();

        session.setConnectionInfo(connectedData.hashCode());

        session.save();

        ctx.session().put(SESSION_FIELD_NAME, session.getId());
    }

    public Session getSession(Http.Context ctx) {
        String sessionId = ctx.session().get(SESSION_FIELD_NAME);

        if (sessionId == null || sessionId.isEmpty()) {
            return null;
        }

        Session session = sessionRepository.getById(sessionId);

        if (session == null) {
            return null;
        }

        UserAgent userAgent = new UserAgent(ctx.request().getHeader(Http.HeaderNames.USER_AGENT));
        String connectedData = userAgent.toString() + ctx.request().remoteAddress();
        if( session.getConnectionInfo() == connectedData.hashCode() ) {
            return session;
        }

        return null;
    }

    public void clear(Http.Context ctx) {
        ctx.session().clear();
    }
}
