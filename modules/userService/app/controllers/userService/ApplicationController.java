package controllers.userService;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author fabiomazzone
 * @version 1.0, 07.07.17
 */
public class ApplicationController extends Controller {
    public Result index() {
        return ok();
    }
}
