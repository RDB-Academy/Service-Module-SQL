package controllers.admin;

import authenticators.Authenticated;
import forms.LoginForm;
import models.Session;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * @author fabiomazzone
 */
public class SessionController extends Controller{
    private final SessionService sessionService;
    private final FormFactory formFactory;

    @Inject
    public SessionController(
            FormFactory formFactory,
            SessionService sessionService) {
        this.formFactory = formFactory;
        this.sessionService = sessionService;
    }

    public Result login() {
        Form<LoginForm> loginForm = formFactory.form(LoginForm.class);
        Session session = sessionService.getSession(ctx());
        if (session != null) {
            return redirect(routes.HomeController.index());
        } else {
            HashMap<String, String> anyData = new HashMap<>();
            anyData.put("email", "test1@test.de");
            anyData.put("password", "password");
            loginForm = loginForm.bind(anyData);
            return ok(views.html.admin.sessionViews.login.render(loginForm));
        }

    }

    @Security.Authenticated(Authenticated.class)
    public Result logout() {
        this.sessionService.clear(ctx());
        return redirect(routes.SessionController.login());
    }
}
