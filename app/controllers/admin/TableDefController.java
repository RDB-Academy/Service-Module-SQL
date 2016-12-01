package controllers.admin;

import authenticators.Authenticated;
import models.TableDef;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.TableDefService;
import services.tools.ServiceError;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
@Security.Authenticated(Authenticated.class)
public class TableDefController extends Controller {
    private final TableDefService tableDefService;

    @Inject
    public TableDefController(TableDefService tableDefService) {
        this.tableDefService = tableDefService;
    }

    public Result read(Long id) {
        Form<TableDef> tableDefForm = this.tableDefService.getViewForm(id);

        // TODO:
        if(tableDefForm == null) {
            return redirect(routes.SchemaDefController.index());
        }

        return ok(views.html.admin.tableDefViews.read.render(tableDefForm));
    }

    public Result edit(Long id) {
        Form<TableDef> tableDefForm = this.tableDefService.getViewForm(id);

        if (tableDefForm == null) {
            return notFound();
        }

        return ok(views.html.admin.tableDefViews.edit.render(tableDefForm));
    }

    public Result delete(Long id) {

        TableDef tableDef = this.tableDefService.getById(id);
        Long schemaID = tableDef.getSchemaDefId();
        ServiceError serviceError = this.tableDefService.deleteTableDef(id);
        // ToDO

        return ok();
    }

}
