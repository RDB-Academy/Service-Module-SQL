package controllers.admin;

import forms.LoginForm;
import models.Session;
import play.Configuration;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.SessionService;

import javax.inject.Inject;

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
            return ok(views.html.sessionController.login.render(loginForm));
        }

    }

    public Result logout() {
        this.sessionService.clear(ctx());
        return redirect(routes.SessionController.login());
    }
}
