package controllers.admin;

/**
 * Created by nicolenaczk on 03.11.16.
 */
public class HomeController {

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author fabiomazzone
 */
public class HomeController extends Controller {

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
        return ok(views.html.login.render());
    }
}
