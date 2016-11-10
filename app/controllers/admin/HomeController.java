package controllers.admin;

import forms.LoginForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Created by nicolenaczk on 03.11.16.
 */

public class HomeController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result index() {
        // ToDo
        // If is Logged in
        // Show Index
        // Else
        return ok(views.html.index.render());
    }

    public Result login() {
        // TODO
        // If has session
        // forward to index
        // Else
        Form<LoginForm> loginForm = formFactory.form(LoginForm.class);
        return ok(views.html.login.render(loginForm));
    }

    public Result getLogin() {
        Form<LoginForm> requestData = formFactory.form(LoginForm.class).bindFromRequest();
        LoginForm loginForm = requestData.get();

        return ok("Hello " + loginForm);

    }
}
