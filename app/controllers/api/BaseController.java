package controllers.api;

import models.BaseModel;
import models.Session;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import services.SessionService;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author Fabio Mazzone
 */
public abstract class BaseController extends Controller
{
    protected final SessionService sessionService;

    BaseController(SessionService sessionService)
    {
        this.sessionService = sessionService;
    }

    Session getSession(@NotNull Http.Request request)
    {
        String sessionId = request.getHeader(SessionService.SESSION_FIELD_NAME);
        if(sessionId != null && !sessionId.isEmpty()) {
            return sessionService.findActiveSessionById(sessionId);
        }
        return null;
    }

    protected Map<String, List<String>> extractParameters(
            Class model, Map<String, String[]> inputParameters) {
        if (!BaseModel.class.isAssignableFrom(model)) {
            Logger.error("Wrong class");
        }

        Set<String> validKeys = new HashSet<>();
        Arrays.stream(model.getDeclaredFields()).forEach(field ->
                validKeys.add(field.getName().toLowerCase()));

        Logger.debug(Arrays.asList(validKeys.toArray()).toString());

        Map<String, List<String>> outputParameters = new HashMap<>();
        inputParameters.forEach((key, val) -> {
            if (!validKeys.contains(key)) {
                return;
            }

            String command = val[0];
            if (command.isEmpty()) {
                return;
            } else if (command.startsWith("<")) {
                List<String> targetValues = new ArrayList<>();
                targetValues.add("lt");
                targetValues.add(command.substring(1));
                outputParameters.put(key, targetValues);
            } else if (command.startsWith(">")) {
                List<String> targetValues = new ArrayList<>();
                targetValues.add("gt");
                targetValues.add(command.substring(1));
                outputParameters.put(key, targetValues);
            } else if (command.startsWith("=")) {
                List<String> targetValues = new ArrayList<>();
                targetValues.add("eq");
                targetValues.add(command.substring(1));
                outputParameters.put(key, targetValues);
            }
        });

        return outputParameters;
    }
}
