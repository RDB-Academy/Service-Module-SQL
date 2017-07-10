import com.typesafe.config.Config;
import play.Environment;
import play.Logger;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.http.DefaultHttpErrorHandler;
import play.api.routing.Router;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 * @version 1.0, 06.07.17
 */
@Singleton
public class ErrorHandler extends DefaultHttpErrorHandler {

    private UserServiceErrorHandler        userServiceErrorHandler;
    private SqlTrainerServiceErrorHandler  sqlTrainerServiceErrorHandler;

    @Inject
    public ErrorHandler(
            Config                  config,
            Environment             environment,
            OptionalSourceMapper    sourceMapper,
            Provider<Router>        routes,
            UserServiceErrorHandler         userServiceErrorHandler,
            SqlTrainerServiceErrorHandler   sqlTrainerServiceErrorHandler) {
        super(config, environment, sourceMapper, routes);

        this.userServiceErrorHandler = userServiceErrorHandler;
        this.sqlTrainerServiceErrorHandler = sqlTrainerServiceErrorHandler;
    }


    private String getSubdomain(String domain) {
        Integer tdlDot = domain.lastIndexOf("."); // Top-Level-Domain Dot
        Integer subDomainDot = domain.indexOf(".");

        if (subDomainDot == -1) {
            return "";
        }

        if (domain.substring(tdlDot + 1).contentEquals("localhost")) { // Check TDL
            subDomainDot = tdlDot;
        }

        return domain.substring(0, subDomainDot);
    }

    @Override
    protected CompletionStage<Result> onNotFound(Http.RequestHeader request, String message) {
        Logger.debug(request.host());
        switch (getSubdomain(request.host())) {
            case "sql":
                return this.sqlTrainerServiceErrorHandler.onNotFound(request, message);
            default:
                return this.userServiceErrorHandler.onNotFound(request, message);
        }
    }

    @Override
    protected CompletionStage<Result> onProdServerError(Http.RequestHeader request, UsefulException exception) {
        Logger.debug(request.host());
        switch (getSubdomain(request.host())) {
            case "sql":
                return this.sqlTrainerServiceErrorHandler.onProdServerError(request, exception);
            default:
                return this.userServiceErrorHandler.onProdServerError(request, exception);
        }
    }
}
