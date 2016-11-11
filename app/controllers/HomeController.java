package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class HomeController extends Controller {
    public Result index() {
        return redirect("/web/");
    }
    public Result webApp(String path) {
        return ok(new java.io.File("public/dist/index.html"));
    }
    public Result webAppRedirect(String path) {
        return redirect("/web/" + path);
    }
}
