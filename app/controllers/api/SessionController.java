package controllers.api;

import forms.LoginForm;
import play.Configuration;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.SessionService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

/**
 * @author fabiomazzone
 */
public class SessionController extends Controller {
    private final FormFactory formFactory;
    private final Configuration configuration;
    private final SessionService sessionService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public SessionController(
            FormFactory formFactory,
            Configuration configuration,
            SessionService sessionService,
            HttpExecutionContext httpExecutionContext) {

        this.formFactory = formFactory;
        this.configuration = configuration;
        this.sessionService = sessionService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result authenticate() {
        Form<LoginForm> loginData = formFactory.form(LoginForm.class).bindFromRequest();

        if (loginData.hasErrors()) {
            if (request().accepts(Http.MimeTypes.TEXT)) {
                return badRequest(views.html.admin.sessionViews.login.render(loginData));
            }
            return badRequest(loginData.errorsAsJson());
        }

        LoginForm loginForm = loginData.get();
        String adminPassword = configuration.getString("sqlModule.adminPassword");

        if (loginForm.getPassword().equals(adminPassword)) {
            sessionService.setAdminSession(loginForm, ctx());
            if(request().accepts(Http.MimeTypes.TEXT)) {
                return redirect(controllers.admin.routes.HomeController.index());
            }
            return ok("Ok");
        } else {
            loginData.reject("Wrong E-Mail or Password");
            if(request().accepts(Http.MimeTypes.TEXT)) {
                return badRequest(views.html.admin.sessionViews.login.render(loginData));
            }
            return badRequest(loginData.errorsAsJson());

        }
    }

    public CompletableFuture<Result> logout() {
        return CompletableFuture
                .supplyAsync(this.sessionService::logout, this.httpExecutionContext.current())
                .thenApply((status) -> {
                    if(request().accepts(Http.MimeTypes.TEXT)) {
                        redirect(controllers.admin.routes.SessionController.login());
                    }
                    return ok();
                });
    }
}
