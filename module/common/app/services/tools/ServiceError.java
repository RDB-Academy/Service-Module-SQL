package services.tools;

import play.mvc.Http;

/**
 * @author fabiomazzone
 */
public class ServiceError {
    public static final int NotFound = 1;
    private final Integer status;
    private final String model;
    private final Long modelId;

    public ServiceError(int status, String model, Long id) {
        this.status = status;
        this.model  = model;
        this.modelId= id;
    }

    public void flash(Http.Context ctx) {
        String message = statusToString(status) + " " + model + " with id: " + modelId + "!";
        ctx.flash().put("error", message);
    }

    private static String statusToString(int status) {
        switch (status) {
            case NotFound:
                return "Not Found";
        }
        return "Unknown Error";
    }
}
