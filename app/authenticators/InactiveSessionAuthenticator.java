package authenticators;

import com.google.inject.Inject;
import models.Session;
import play.mvc.Http;
import services.SessionService;

/**
 * @authur fabiomazzone
 */
public class InactiveSessionAuthenticator extends SessionAuthenticator {
    @Inject
    InactiveSessionAuthenticator(SessionService sessionService) {
        super(sessionService);
    }

    @Override
    public String getUsername(Http.Context ctx) {
        Session session = this.getSessionByRequest(ctx.request());
        if(session != null && this.sessionService.isActive(session)) {
            return null;
        }
        return "";
    }
}
