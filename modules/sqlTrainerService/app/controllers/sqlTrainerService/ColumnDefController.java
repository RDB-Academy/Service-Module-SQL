package controllers.sqlTrainerService;

import com.google.inject.Singleton;
import models.sqlTrainerService.ColumnDef;
import models.sqlTrainerService.TableDef;
import models.sqlTrainerService.UserData;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import repositories.sqlTrainerService.ColumnDefRepository;
import repositories.sqlTrainerService.TableDefRepository;
import services.sqlTrainerService.ColumnDefService;
import services.sqlTrainerService.UserDataService;

import javax.inject.Inject;

/**
 * @author invisible
 */
@Singleton
public class ColumnDefController extends ServiceController {
    private final ColumnDefRepository columnDefRepository;
    private final ColumnDefService columnDefService;
    private final FormFactory formFactory;
    private final TableDefRepository tableDefRepository;

    @Inject
    ColumnDefController(
            UserDataService         userDataService,
            ColumnDefRepository     columnDefRepository,
            ColumnDefService        columnDefService,
            FormFactory             formFactory,
            TableDefRepository      tableDefRepository) {
        super(userDataService);

        this.columnDefRepository    = columnDefRepository;
        this.columnDefService       = columnDefService;
        this.formFactory            = formFactory;
        this.tableDefRepository     = tableDefRepository;
    }

    public Result create() {
        Form<ColumnDef> columnDefForm = this.formFactory.form(ColumnDef.class).bindFromRequest();
        ColumnDef columnDef = columnDefForm.get();

        if(!columnDefForm.hasErrors()) {
            Long TableDefId = columnDef.getTableDefId();
            TableDef tableDef = this.tableDefRepository.getById(TableDefId);
            if(tableDef == null) {
                columnDefForm.withGlobalError("SchemaDef not found");
            } else {
                columnDef.setTableDef(tableDef);
            }
        }

        if(columnDefForm.hasErrors()) {
            Logger.warn("TableDefForm has errors");
            Logger.warn(columnDefForm.errorsAsJson().toString());
            return badRequest(columnDefForm.errorsAsJson());
        }

        this.columnDefRepository.save(columnDef);

        return ok(columnDefService.transformBase(columnDef));
    }

    public Result read(Long id) {
        ColumnDef   columnDef   = this.columnDefRepository.getById(id);
        UserData    user        = this.getUserData(ctx().args);

        if(columnDef == null) {
            return notFound();
        }

        if(user != null) {
            return ok(this.columnDefService.transform(columnDef));
        }
        return ok(Json.toJson(columnDef));
    }

    public Result update(Long id) {
        return TODO;
    }

    public Result delete(Long id)
    {
        return TODO;
    }
}
