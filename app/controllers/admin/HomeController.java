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
        return ok(views.html.homeController.index.render());
    }
}
