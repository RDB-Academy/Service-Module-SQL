import play.Logger;
import play.api.http.JavaCompatibleHttpRequestHandler;
import play.http.DefaultHttpRequestHandler;
import play.http.HandlerForRequest;
import play.mvc.Http;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 * @version 1.0, 10.07.17
 */
public class SqlTrainerServiceRequestHandler extends DefaultHttpRequestHandler{
    @Inject
    public SqlTrainerServiceRequestHandler(JavaCompatibleHttpRequestHandler underlying) {
        super(underlying);
    }

    @Override
    public HandlerForRequest handlerForRequest(Http.RequestHeader request) {
        Logger.info("Hello from SQL Trainer");
        return super.handlerForRequest(request);
    }
}
