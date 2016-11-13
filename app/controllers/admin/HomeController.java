package controllers.admin;

import authenticators.SessionAuthenticators;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * @author fabiomazzone
 */
@Security.Authenticated(SessionAuthenticators.class)
public class HomeController extends Controller {
    public Result index() {
        String user = session("connected");

        if (user != null) {
            return ok(views.html.index.render());
        } else {
            return redirect(routes.SessionController.login());
        }
    }
}
