package controllers.admin;

import models.SchemaDef;
import play.Logger;
import play.data.Form;
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
    private final SchemaDefRepository schemaDefRepository;
    private final FormFactory formFactory;

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

        flash("notFound", "Schema with id " + id + " not found!");
        return redirect(routes.SchemaDefController.index());
    }

    public Result edit(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);
        if(schemaDef != null) {
            return ok(views.html.schemaDefController.edit.render(formFactory.form(SchemaDef.class).fill(schemaDef)));
        }
        flash("notFound", "Schema with id " + id + " not found!");
        return redirect(routes.SchemaDefController.index());
    }

    public Result update(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);
        Form<SchemaDef> schemaDefForm = this.formFactory.form(SchemaDef.class).bindFromRequest();

        if(schemaDefForm.hasErrors()) {
            return badRequest(views.html.schemaDefController.edit.render(schemaDefForm));
        }

        return redirect(routes.SchemaDefController.show(2L));
    }

    public Result delete(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);
        Logger.info("Delete");
        if(schemaDef == null) {
            flash("notFound", "Schema with ID " + id + " not found!");
        }
        return redirect(routes.SchemaDefController.index());
    }
}
