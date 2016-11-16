package controllers.admin;

import authenticators.Authenticated;
import models.TableDef;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repository.TableDefRepository;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
@Security.Authenticated(Authenticated.class)
public class TableDefController extends Controller {
    private TableDefRepository tableDefRepository;
    private FormFactory formFactory;

    @Inject
    public TableDefController(TableDefRepository tableDefRepository, FormFactory formFactory) {
        this.tableDefRepository = tableDefRepository;
        this.formFactory = formFactory;
    }

    public Result view(Long id) {
        TableDef tableDef = tableDefRepository.getById(id);

        if(tableDef == null) {
            return notFound();
        }

        return ok(views.html.admin.tableDefViews.view.render(this.formFactory.form(TableDef.class).fill(tableDef)));
    }

    public Result edit(Long id) {
        TableDef tableDef = tableDefRepository.getById(id);

        if (tableDef == null) {
            return notFound();
        }

        return ok(views.html.admin.tableDefViews.edit.render(this.formFactory.form(TableDef.class).fill(tableDef)));
    }

}
