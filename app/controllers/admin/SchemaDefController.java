package controllers.admin;

import models.SchemaDef;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * @author fabiomazzone
 */
public class SchemaDefController extends Controller {
    private SchemaDefRepository schemaDefRepository;
    private FormFactory formFactory;

    @Inject
    public SchemaDefController(FormFactory formFactory, SchemaDefRepository schemaDefRepository) {
        this.schemaDefRepository = schemaDefRepository;
        this.formFactory = formFactory;
    }

    public Result index() {
        List<SchemaDef> schemaDefList = this.schemaDefRepository.getAll();
        return ok(views.html.schemaDefController.index.render(schemaDefList));
    }

    public Result show(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);
        if(schemaDef != null) {
            return ok(views.html.schemaDefController.view.render(formFactory.form(SchemaDef.class).fill(schemaDef)));
        }
        flash("status", "cannot find Schema with id " + id);
        return redirect(routes.SchemaDefController.index());
    }
}
