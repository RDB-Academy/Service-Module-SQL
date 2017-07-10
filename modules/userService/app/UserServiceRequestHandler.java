import play.api.http.JavaCompatibleHttpRequestHandler;
import play.http.DefaultHttpRequestHandler;
import play.http.HandlerForRequest;
import play.mvc.Http;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 * @version 1.0, 10.07.17
 */
public class UserServiceRequestHandler extends DefaultHttpRequestHandler {
    private  JavaCompatibleHttpRequestHandler underlying;

    @Inject
    public UserServiceRequestHandler(JavaCompatibleHttpRequestHandler underlying) {
        super(underlying);

        this.underlying = underlying;
    }


    @Override
    public HandlerForRequest handlerForRequest(Http.RequestHeader request) {
        this.underlying.
        return super.handlerForRequest(request);
    }
}
