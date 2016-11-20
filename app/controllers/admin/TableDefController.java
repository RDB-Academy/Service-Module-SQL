package controllers.admin;

import authenticators.Authenticated;
import models.TableDef;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.TableDefService;

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

    public Result view(Long id) {
        Form<TableDef> tableDefForm = this.tableDefService.getViewForm(id);

        if(tableDefForm == null) {
            return notFound();
        }

        return ok(views.html.admin.tableDefViews.view.render(tableDefForm));
    }

    public Result edit(Long id) {
        Form<TableDef> tableDefForm = this.tableDefService.getViewForm(id);

        if (tableDefForm == null) {
            return notFound();
        }

        return ok(views.html.admin.tableDefViews.edit.render(tableDefForm));
    }

}
