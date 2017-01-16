package controllers.admin;

import authenticators.Authenticated;
import forms.LoginForm;
import models.Session;
import play.data.Form;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
public class SessionController extends Controller{
    private final SessionService sessionService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public SessionController(
            SessionService sessionService,
            HttpExecutionContext httpExecutionContext) {

        this.sessionService = sessionService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result login() {
        if(this.sessionService.isLoggedIn(ctx())) {
            return redirect(routes.HomeController.index());
        }
        Form<LoginForm> loginForm = this.sessionService.getLoginForm();
        return ok(views.html.admin.sessionViews.login.render(loginForm));
    }

    public Result authenticate() {
        Form<LoginForm> loginForm = this.sessionService.validateLoginForm();
        if(loginForm.hasErrors()) {
            return badRequest(views.html.admin.sessionViews.login.render(loginForm));
        }

        Session session = this.sessionService.login(loginForm);

        if(session == null) {
            return badRequest(views.html.admin.sessionViews.login.render(loginForm));
        }

        return redirect(routes.HomeController.index());
    }

    @Security.Authenticated(Authenticated.class)
    public CompletionStage<Result> logout() {
        return CompletableFuture
                .supplyAsync(this.sessionService::logout, this.httpExecutionContext.current())
                .thenApply((Boolean status) -> redirect(routes.SessionController.login()));
    }
}
