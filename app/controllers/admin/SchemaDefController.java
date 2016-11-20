package controllers.admin;

import authenticators.Authenticated;
import models.SchemaDef;
import models.TableDef;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repository.SchemaDefRepository;
import services.SchemaDefService;
import services.tools.ServiceError;

import javax.inject.Inject;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Security.Authenticated(Authenticated.class)
public class SchemaDefController extends Controller {
    private final SchemaDefService schemaDefService;

    @Inject
    public SchemaDefController(
            SchemaDefService schemaDefService,
            SchemaDefRepository schemaDefRepository) {

        this.schemaDefService = schemaDefService;
    }


    public Result list() {
        List<SchemaDef> schemaDefList = this.schemaDefService.getSchemaDefList();
        return ok(views.html.admin.schemaDefViews.list.render(schemaDefList));
    }

    public Result createForm() {
        return ok(views.html.admin.schemaDefViews.createForm.render(this.schemaDefService.getCreateForm()));
    }

    public Result createJson() {
        return ok(views.html.admin.schemaDefViews.createJson.render());
    }

    public Result newSchemaDef() {
        Form<SchemaDef> schemaDefForm = this.schemaDefService.getNewSchemaDef(request());

        if(schemaDefForm.hasErrors()) {
            if(request().body().asJson() == null) {
                flash("flash", "Error");
                return redirect(routes.SchemaDefController.createJson());
            }
            return badRequest(views.html.admin.schemaDefViews.createForm.render(schemaDefForm));
        }

        Long id = schemaDefForm.get().getId();
        return redirect(routes.SchemaDefController.view(id));
    }

    public Result view(Long id) {
        Form<SchemaDef> schemaDefForm = this.schemaDefService.getSchemaDefForm(id);

        if(schemaDefForm == null) {
            flash("notFound", "Schema with id " + id + " not found!");
            return redirect(routes.SchemaDefController.list());
        }

        return ok(views.html.admin.schemaDefViews.view.render(schemaDefForm));
    }

    public Result edit(Long id) {
        Form<SchemaDef> schemaDefForm = this.schemaDefService.getSchemaDefForm(id);

        if(schemaDefForm == null) {
            flash("notFound", "Schema with id " + id + " not found!");
            return redirect(routes.SchemaDefController.list());
        }

        return ok(views.html.admin.schemaDefViews.edit.render(schemaDefForm));
    }

    public Result update(Long id) {



        return redirect(routes.SchemaDefController.edit(id));
    }

    public Result delete(Long id) {
        ServiceError serviceError = this.schemaDefService.deleteSchemaDef(id);

        if(serviceError != null) {
            serviceError.flash(ctx());
        }

        return redirect(routes.SchemaDefController.list());
    }

    public Result createTableDef(long id) {
        Form<TableDef> tableDefForm = this.schemaDefService.getCreateTableDefForm(id);

        if(tableDefForm == null) {
            return notFound();
        }

        return ok(views.html.admin.schemaDefViews.createTableDef.render(tableDefForm));
    }
}
