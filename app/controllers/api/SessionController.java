package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import authenticators.InactiveSessionAuthenticator;
import forms.LoginForm;
import models.Session;
import play.data.Form;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import repositories.SessionRepository;
import services.SessionService;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
public class SessionController extends BaseController {
    private final SessionRepository sessionRepository;
    @Inject
    public SessionController(
            SessionService sessionService,
            SessionRepository sessionRepository)
    {
        super(sessionService);

        this.sessionRepository = sessionRepository;
    }

    @Security.Authenticated(InactiveSessionAuthenticator.class)
    public Result login() {
        Form<LoginForm> loginForm = this.sessionService.validateLoginForm();
        if(loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }

        Session session = this.sessionService.login(loginForm);

        if(session == null) {
            return internalServerError();
        }

        return ok(Json.toJson(session));
    }

    @Security.Authenticated(ActiveSessionAuthenticator.class)
    public Result logout() {
        Session session = this.getSession(request());
        this.sessionService.invalidate(session);
        this.sessionRepository.save(session);
        return ok();
    }
}
