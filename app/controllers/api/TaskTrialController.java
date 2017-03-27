package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import models.Session;
import models.TaskTrial;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import repository.TaskTrialRepository;
import services.SessionService;
import services.TaskTrialService;

import javax.inject.Inject;

/**
 *
 * @author Fabio Mazzone
 */
@Security.Authenticated(ActiveSessionAuthenticator.class)
public class TaskTrialController extends BaseController {
    private final TaskTrialService taskTrialService;
    private final TaskTrialRepository taskTrialRepository;

    @Inject
    public TaskTrialController(
            SessionService sessionService,
            TaskTrialService taskTrialService,
            TaskTrialRepository taskTrialRepository)
    {
        super(sessionService);

        this.taskTrialService = taskTrialService;
        this.taskTrialRepository = taskTrialRepository;
    }

    /**
     * API Endpoint
     *  POST /taskTrial
     *
     * @return returns an status code with a message
     */
    public Result create()
    {
        Session session = this.getSession(request());

        if(sessionService.isAdmin(session))
        {

        }

        TaskTrial taskTrial = this.taskTrialService.create();

        if(taskTrial == null)
        {
            return internalServerError();
        }
        return ok(Json.toJson(taskTrial));
    }

    public Result read(Long id)
    {
        TaskTrial taskTrial = this.taskTrialRepository.getById(id);

        if(taskTrial == null)
        {
            return notFound("No such object available!");
        }
        return ok(Json.toJson(taskTrial));
    }

    public Result update(Long id)
    {
        TaskTrial taskTrial = this.taskTrialService.validateStatement(id);

        if(taskTrial == null)
        {
            return badRequest("Something went terribly wrong");
        }
        if(taskTrial.getIsFinished())
        {
            return ok(Json.toJson(taskTrial));
        }
        if(taskTrial.getTaskTrialStatus().getStatement() == null
                || taskTrial.getTaskTrialStatus().getStatement().isEmpty())
        {
            return badRequest("Submitted Statement is Empty");
        }

        return ok(Json.toJson(taskTrial));
    }
}
