package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Singleton;
import java.io.File;

/**
 * @author fabiomazzone
 */
@Singleton
public class HomeController extends Controller {
    public Result index() {
        return redirect("/web/");
    }
    public Result webApp(String path) {
        File file = new File("public/dist/" + path);
        if (file.exists()) {
            return ok(file);
        }
        return ok(new java.io.File("public/dist/index.html"));
    }
    public Result webAppRedirect(String path) {
        return redirect("/web/" + path);
    }
}
