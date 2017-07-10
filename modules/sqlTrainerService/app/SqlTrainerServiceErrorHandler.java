import com.typesafe.config.Config;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;


/**
 * @author fabiomazzone
 * @version 1.0, 07.07.17
 */
@Singleton
public class SqlTrainerServiceErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public SqlTrainerServiceErrorHandler(
            Config config,
            Environment environment,
            OptionalSourceMapper sourceMapper,
            Provider<Router> routes) {
        super(config, environment, sourceMapper, routes);
    }

    @Override
    public CompletionStage<Result> onNotFound(Http.RequestHeader request, String message) {
        return super.onNotFound(request, message);
    }

    @Override
    public  CompletionStage<Result> onProdServerError(Http.RequestHeader request, UsefulException exception) {
        return super.onProdServerError(request, exception);
    }
}
