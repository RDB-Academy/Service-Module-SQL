package controllers.admin;

import authenticators.Authenticated;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * @author fabiomazzone
 */
@Security.Authenticated(Authenticated.class)
public class HomeController extends Controller {
    public Result index() {
        return ok(views.html.homeController.index.render());
    }
}
