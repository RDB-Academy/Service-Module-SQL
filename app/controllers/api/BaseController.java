package controllers.api;

import models.Session;
import play.mvc.Controller;
import play.mvc.Http;
import services.SessionService;

import javax.validation.constraints.NotNull;

/**
 * @author Fabio Mazzone
 */
public abstract class BaseController extends Controller
{
    protected final SessionService sessionService;

    BaseController(SessionService sessionService)
    {
        this.sessionService = sessionService;
    }

    Session getSession(@NotNull Http.Request request)
    {
        String sessionId = request.getHeader(SessionService.SESSION_FIELD_NAME);
        if(sessionId != null && !sessionId.isEmpty()) {
            return sessionService.findActiveSessionById(sessionId);
        }
        return null;
    }
}
