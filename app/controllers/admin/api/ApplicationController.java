package controllers.admin.api;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author fabiomazzone
 */
public class ApplicationController extends Controller {
    public Result notFoundView(String file) {
        Logger.warn("404 at Path: " + file);
        return notFound();
    }
}
