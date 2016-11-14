package controllers.admin;

import forms.LoginForm;
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
    private FormFactory formFactory;

    @Inject
    public SessionController(
            FormFactory formFactory) {
        this.formFactory = formFactory;
    }


    public Result login() {
        Form<LoginForm> loginForm = formFactory.form(LoginForm.class);

        Logger.info(flash("forbidden"));

        String user = session("");
        if (user != null) {
            return redirect(routes.HomeController.index());
        } else {
            return ok(views.html.login.render(loginForm));
        }

    }

    public Result logout() {
        String user = session("connected");
        if (user != null) {
            session().clear();
            return redirect(routes.SessionController.login());
        }
        return ok();
    }
}
