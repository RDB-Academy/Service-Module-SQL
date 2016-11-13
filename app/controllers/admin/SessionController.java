package controllers.admin;

import forms.LoginForm;
import play.Configuration;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
public class SessionController extends Controller{
    private FormFactory formFactory;
    private Configuration configuration;

    @Inject
    public SessionController(
            FormFactory formFactory,
            Configuration configuration) {
        this.formFactory = formFactory;
        this.configuration = configuration;
    }
    public Result login() {
        Form<LoginForm> loginForm = formFactory.form(LoginForm.class);

        String user = session("connected");
        if (user != null) {
            return redirect(routes.HomeController.index());
        } else {
            return ok(views.html.login.render(loginForm));
        }

    }

    public Result performLogin() {
        Form<LoginForm> requestData = formFactory.form(LoginForm.class).bindFromRequest();
        String adminPassword = configuration.getString("sqlModule.adminPassword");

        if (requestData.hasErrors()) {
            return badRequest(views.html.login.render(requestData));
        }

        LoginForm loginForm = requestData.get();

        if (loginForm.getPassword().equals(adminPassword)) {
            session("connected", "userID");
            return redirect(routes.HomeController.index());
        } else {
            return badRequest(views.html.login.render(requestData));
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
