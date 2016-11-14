package authenticators;

import models.Session;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
public class SessionAuthenticators extends Security.Authenticator {
    private SessionService sessionService;

    @Inject
    public SessionAuthenticators(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public String getUsername(Http.Context ctx) {
        Session session = this.sessionService.getSession(ctx);
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        ctx.flash().put("forbidden", "error");
        return redirect(controllers.admin.routes.SessionController.login());
    }
}
