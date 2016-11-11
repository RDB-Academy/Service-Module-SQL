package controllers.admin;

import form.LoginForm;
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

        String user = session("connected");

        if (user != null) {
            return ok(views.html.index.render());
        } else {
            return redirect(routes.HomeController.login());
        }

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

    public Result getLogin() {
        Form<LoginForm> requestData = formFactory.form(LoginForm.class).bindFromRequest();

        if (requestData.hasErrors()) {
            return badRequest(views.html.login.render(requestData));
        }

        LoginForm loginForm = requestData.get();

        //TODO: insert here

        return ok();

    }

    public Result logout() {

        String user = session("connected");
        if (user != null) {
            session().clear();
            return redirect(routes.HomeController.login());
        }
        return ok();
    }

}
