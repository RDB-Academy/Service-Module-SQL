package controllers.api;

import com.google.inject.Singleton;
import models.Session;
import models.TableDef;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import repository.TableDefRepository;
import services.SessionService;
import services.TableDefService;

import javax.inject.Inject;

/**
 * @author invisible
 */
@Singleton
public class TableDefController extends RootController
{
    private final TableDefService tableDefService;
    private final TableDefRepository tableDefRepository;

    @Inject
    public TableDefController(
            TableDefService tableDefService,
            TableDefRepository tableDefRepository,
            SessionService sessionService)
    {
        super(sessionService);

        this.tableDefService = tableDefService;
        this.tableDefRepository = tableDefRepository;
    }


    public Result read(Long id)
    {
        TableDef tableDef = this.tableDefRepository.getById(id);

        if(tableDef == null) {
            return notFound();
        }
        Session session = this.getSession(Http.Context.current().request());
        if(session != null && sessionService.isAdmin(session)) {
            return ok(this.tableDefService.transform(tableDef));
        }
        return ok(Json.toJson(tableDef));
    }
}
