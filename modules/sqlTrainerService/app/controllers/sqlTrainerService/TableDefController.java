package controllers.sqlTrainerService;

import com.google.inject.Singleton;
import models.sqlTrainerService.SchemaDef;
import models.sqlTrainerService.TableDef;
import models.sqlTrainerService.UserData;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import repositories.sqlTrainerService.SchemaDefRepository;
import repositories.sqlTrainerService.TableDefRepository;
import services.sqlTrainerService.TableDefService;
import services.sqlTrainerService.UserDataService;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 *
 * @author invisible
 */
@Singleton
public class TableDefController extends ServiceController {
    private final TableDefService tableDefService;
    private final TableDefRepository tableDefRepository;
    private final SchemaDefRepository schemaDefRepository;
    private final FormFactory formFactory;

    @Inject
    TableDefController(
            UserDataService userDataService,
            TableDefService tableDefService,
            TableDefRepository tableDefRepository,
            SchemaDefRepository schemaDefRepository,
            FormFactory formFactory) {
        super(userDataService);
        this.tableDefService = tableDefService;
        this.tableDefRepository = tableDefRepository;
        this.schemaDefRepository = schemaDefRepository;
        this.formFactory = formFactory;
    }


    /**
     *
     *
     * @return returns the new created TableDef or an error
     */
    public Result create()
    {
        Form<TableDef> tableDefForm = this.formFactory.form(TableDef.class).bindFromRequest();
        TableDef tableDef = tableDefForm.get();

        if(!tableDefForm.hasErrors()) {
            Long SchemaDefId = tableDef.getSchemaDefId();
            SchemaDef schemaDef = this.schemaDefRepository.getById(SchemaDefId);
            if(schemaDef == null) {
                tableDefForm.withGlobalError("SchemaDef not found");
            } else {
                tableDef.setSchemaDef(schemaDef);
            }
        }

        if(tableDefForm.hasErrors()) {
            Logger.warn("TableDefForm has errors");
            Logger.warn(tableDefForm.errorsAsJson().toString());
            return badRequest(tableDefForm.errorsAsJson());
        }

        this.tableDefRepository.save(tableDef);

        return ok(tableDefService.transformBase(tableDef));
    }

    public Result readAll() {
        Map<String, List<String>> params =
                extractKnownParameters(TableDef.class, request().queryString());

        Logger.debug("readAll");

        params.forEach((key, val) -> {
            Logger.debug(key + val);
        });

        return ok();
    }

    public Result read(Long id) {
        UserData userData = this.getUserData(ctx().args);
        TableDef tableDef = this.tableDefRepository.getById(id);

        if(tableDef == null)
        {
            return notFound();
        }

        if(userData != null)
        {
            return ok(this.tableDefService.transform(tableDef));
        }
        return ok(Json.toJson(tableDef));
    }

    public Result update(Long id)
    {
        TableDef        tableDef
                = this.tableDefRepository.getById(id);
        Form<TableDef>  tableDefForm
                = this.formFactory.form(TableDef.class).bindFromRequest(
                        request(), "name", "extensionDef");

        if(tableDef == null)
        {
            return notFound();
        }

        if(tableDefForm.hasErrors())
        {
            Logger.warn(tableDefForm.errorsAsJson().toString());
            return badRequest(tableDefForm.errorsAsJson());
        }

        return TODO;
    }

    public Result delete(Long id)
    {
        TableDef tableDef = this.tableDefRepository.getById(id);
        if(tableDef == null)
        {
            return notFound();
        }

        this.tableDefRepository.delete(tableDef);

        return ok();
    }
}
