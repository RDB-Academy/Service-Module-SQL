import play.Logger;
import play.api.mvc.Handler;
import play.http.HandlerForRequest;
import play.http.HttpRequestHandler;
import play.libs.streams.Accumulator;
import play.mvc.EssentialAction;
import play.mvc.Http.RequestHeader;
import play.mvc.Results;


import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author fabiomazzone
 * @version 1.0, 06.07.17
 */
@Singleton
public class VirtualHostRequestHandler implements HttpRequestHandler {

    private userService.Routes          userServiceRouter;
    private sqlTrainerService.Routes    sqlTrainerServiceRouter;

    @Inject
    public VirtualHostRequestHandler(
            userService.Routes          userServiceRouter,
            sqlTrainerService.Routes    sqlTrainerServiceRouter) {

        this.userServiceRouter = userServiceRouter;
        this.sqlTrainerServiceRouter = sqlTrainerServiceRouter;
    }

    private String getSubdomain(String domain) {
        Integer tdlDot          = domain.lastIndexOf("."); // Top-Level-Domain Dot
        Integer subDomainDot    = domain.indexOf(".");

        if ( subDomainDot ==  -1 ) {
            return "";
        }

        if (domain.substring(tdlDot + 1).contentEquals("localhost")) { // Check TDL
            subDomainDot = tdlDot;
        }

        return domain.substring(0, subDomainDot);
    }

    @Override
    public HandlerForRequest handlerForRequest(RequestHeader request) {
        Logger.debug("host: " + request.asScala().domain());
        Handler handler;
        switch (getSubdomain(request.asScala().domain())) {
            case "sql":
                handler = this.sqlTrainerServiceRouter.asJava().route(request).orElseGet(() ->
                        EssentialAction.of(req -> Accumulator.done(Results.notFound()))
                );
                break;
            default:
                handler = this.userServiceRouter.asJava().route(request).orElseGet(() ->
                        EssentialAction.of(req -> Accumulator.done(Results.notFound()))
                );
        }
        return new HandlerForRequest(request, handler);
    }
}
