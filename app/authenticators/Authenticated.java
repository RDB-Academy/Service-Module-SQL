package authenticators;

import models.Session;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
public class Authenticated extends Security.Authenticator {
    private SessionService sessionService;

    @Inject
    public Authenticated(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public String getUsername(Http.Context ctx) {
        Session session = this.sessionService.getSession(ctx);
        return (session != null ) ? session.getUserName() : null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        ctx.flash().put("forbidden", "error");
        Logger.info("Unauthorized Action");
        return redirect(controllers.admin.routes.SessionController.login());
    }
}
