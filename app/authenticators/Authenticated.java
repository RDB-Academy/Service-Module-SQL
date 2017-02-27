package authenticators;

import models.Session;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
public class Authenticated extends Security.Authenticator {
    private final SessionService sessionService;

    @Inject
    public Authenticated(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public String getUsername(Http.Context ctx) {
        Session session = this.sessionService.getSession(ctx);
        if(session == null) return null;
        return (session.getUsername() != null
                && session.getUsername().equals("admin")) ? session.getUsername() : null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        Logger.info("Unauthorized Action");
        if (ctx.request().accepts(Http.MimeTypes.JSON)){
            return unauthorized(Json.toJson("{}"));
        }
        return unauthorized();
    }
}
