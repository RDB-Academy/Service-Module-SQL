package authenticators;

import models.Session;
import play.mvc.Http;
import services.SessionService;

import javax.inject.Inject;

/**
 *  @author Fabio Mazzone
 */
public class AdminSessionAuthenticator extends SessionAuthenticator {
    @Inject
    public AdminSessionAuthenticator(SessionService sessionService)
    {
        super(sessionService);
    }

    @Override
    public String getUsername(Http.Context ctx)
    {
        Session session = this.getSessionByRequest(ctx.request());
        return (session != null && sessionService.isAdmin(session))? session.getUsername() : null;
    }
}
