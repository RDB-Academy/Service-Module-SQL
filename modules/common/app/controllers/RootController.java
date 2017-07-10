package controllers;

import models.BaseModel;
import play.Logger;
import play.mvc.Controller;

import java.util.*;

/**
 * @author Fabio Mazzone
 */
public abstract class RootController extends Controller {
    /**
     * Extracts known parameters from inputParameters.
     * Only keeps parameters existent as property in model.
     * @param model model class to determine possible parameters
     * @param inputParameters parameters from network call
     * @return
     */
    protected Map<String, List<String>> extractKnownParameters(
            Class<? extends BaseModel> model,
            Map<String, String[]> inputParameters) {

        if (!BaseModel.class.isAssignableFrom(model)) {
            Logger.error("Wrong class");
        }

        Set<String> validKeys = new HashSet<>();
        Arrays.stream(
                model.getDeclaredFields()
            ).forEach(field ->
                validKeys.add(field.getName().toLowerCase())
            );

        Map<String, List<String>> outputParameters = new HashMap<>();

        inputParameters.forEach((key, val) ->
        {
            if (!validKeys.contains(key)) {
                return;
            }

            String command = val[0];
            if (command.isEmpty()) {
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
