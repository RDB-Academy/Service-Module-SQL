package controllers.admin;

import authenticators.Authenticated;
import forms.LoginForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
public class SessionController extends Controller{
    private final SessionService sessionService;

    @Inject
    public SessionController(
            SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public Result login() {
        if(this.sessionService.getSession(ctx()) != null) {
            return redirect(routes.HomeController.index());
        }
        Form<LoginForm> loginForm = this.sessionService.getLoginForm();
        return ok(views.html.admin.sessionViews.login.render(loginForm));
    }

    @Security.Authenticated(Authenticated.class)
    public Result logout() {
        this.sessionService.clear(ctx());
        return redirect(routes.SessionController.login());
    }
}
