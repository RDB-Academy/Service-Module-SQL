package authenticators;

import com.google.inject.Inject;
import models.Session;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;

import javax.validation.constraints.NotNull;

abstract public class SessionAuthenticator extends Security.Authenticator {
    final SessionService sessionService;

    @Inject
    SessionAuthenticator(SessionService sessionService)
    {
        this.sessionService = sessionService;
    }

    Session getSessionByRequest(@NotNull Http.Request request)
    {
        String sessionId = request.getHeader(SessionService.SESSION_FIELD_NAME);
        return sessionService.findActiveSessionById(sessionId);
    }

    @Override
    public Result onUnauthorized(Http.Context ctx)
    {
        return super.onUnauthorized(ctx);
    }
}
