package authenticators;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * @author fabiomazzone
 */
public class SessionAuthenticators extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context ctx) {
        //return super.getUsername(ctx);
        return "";  
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return super.onUnauthorized(ctx);
    }
}
