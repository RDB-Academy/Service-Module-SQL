package authenticators;

import models.Session;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;

import javax.inject.Inject;

/**
 *  @author Fabio Mazzone
 */
public class AdminAuthenticator extends Security.Authenticator {
    private final SessionService sessionService;

    @Inject
    public AdminAuthenticator(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public String getUsername(Http.Context ctx) {
        Session session = this.sessionService.getSession(ctx.request());
        return (session != null && sessionService.isAdmin(session))? session.getUsername() : null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return super.onUnauthorized(ctx);
    }
}
