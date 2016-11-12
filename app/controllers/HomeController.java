package controllers;

import extensionMaker.ExtensionMaker;
import models.SchemaDef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

/**
 * @author fabiomazzone
 */
@Singleton
public class HomeController extends Controller {
    @Inject
    SchemaDefRepository schemaDefRepository;

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

    public Result testExtensionMaker() {
        SchemaDef schemaDef = schemaDefRepository.getById(1L);
        ExtensionMaker extensionMake = new ExtensionMaker(1234L);
        extensionMake.test(schemaDef);
        return ok("Toll");
    }
}
