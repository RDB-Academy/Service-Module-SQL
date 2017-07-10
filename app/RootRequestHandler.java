import play.Logger;
import play.http.HandlerForRequest;
import play.http.HttpRequestHandler;
import play.mvc.Http;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author fabiomazzone
 * @version 1.0, 06.07.17
 */
@Singleton
public class RootRequestHandler implements HttpRequestHandler {
    private SqlTrainerServiceRequestHandler sqlTrainerServiceRequestHandler;
    private UserServiceRequestHandler userServiceRequestHandler;

    @Inject
    public RootRequestHandler(
            SqlTrainerServiceRequestHandler sqlTrainerServiceRequestHandler,
            UserServiceRequestHandler userServiceRequestHandler) {

        this.sqlTrainerServiceRequestHandler = sqlTrainerServiceRequestHandler;
        this.userServiceRequestHandler = userServiceRequestHandler;
    }

    /*
  * Gets the subdomain: "admin" o "www"
  */
    private String getSubdomain(String domain) {
        Integer tdlDot = domain.lastIndexOf('.');
        String tdl = "";
        Integer subDomainDot = 0;



        if(tdlDot == -1)
            return "";

        tdl = domain.substring(tdlDot + 1);

        if(tdl.contentEquals("localhost"))
            subDomainDot = tdlDot;

        return domain.substring(0, subDomainDot);
    }


    @Override
    public HandlerForRequest handlerForRequest(Http.RequestHeader request) {
        Logger.info(getSubdomain(request.asScala().domain()));
        switch (getSubdomain(request.asScala().domain())) {
            case "sql":
                return sqlTrainerServiceRequestHandler.handlerForRequest(request);
            default:
                return userServiceRequestHandler.handlerForRequest(request);
        }
    }
}
