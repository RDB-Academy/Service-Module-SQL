package controllers.userService;

import com.google.inject.Inject;
import forms.LoginForm;
import models.Session;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import services.SessionService;

import java.util.Optional;


/**
 * @author fabiomazzone
 */
public class SessionController extends BaseController {

    private FormFactory     formFactory;
    private SessionService  sessionService;

    @Inject
    public SessionController(
            SessionService  sessionService,
            FormFactory     formFactory) {

        this.formFactory    = formFactory;
        this.sessionService = sessionService;
    }

    public Result login() {
        Logger.debug("Test");
        return ok();
        /*Form<LoginForm> loginForm = this.formFactory.form(LoginForm.class).bindFromRequest(ctx().request());

        if(loginForm.hasGlobalErrors() || loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson(ctx().lang()));
        }

        Optional<Session> session = this.sessionService.createSession(ctx(), loginForm);
        if (session.isPresent()) {
            return ok(Json.toJson(session));
        }

        return internalServerError("Try Again Later");*/
    }

    public Result logout() {
        // Session session = this.getSession(request());
        // this.sessionService.invalidate(session);
        return ok();
    }
}
