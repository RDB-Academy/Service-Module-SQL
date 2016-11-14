package controllers.api;

import forms.LoginForm;
import play.Configuration;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.SessionService;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
public class SessionController extends Controller {
    private FormFactory formFactory;
    private Configuration configuration;
    private SessionService sessionService;

    @Inject
    public SessionController(FormFactory formFactory, Configuration configuration, SessionService sessionService) {
        this.formFactory = formFactory;
        this.configuration = configuration;
        this.sessionService = sessionService;
    }

    public Result authenticate() {
        Form<LoginForm> loginData = formFactory.form(LoginForm.class).bindFromRequest();
        String adminPassword = configuration.getString("sqlModule.adminPassword");
        if (loginData.hasErrors()) {
            if (request().accepts(Http.MimeTypes.TEXT)) {
                return badRequest(views.html.sessionController.login.render(loginData));
            }
            return badRequest(loginData.errorsAsJson());
        }

        LoginForm loginForm = loginData.get();

        if (loginForm.getPassword().equals(adminPassword)) {
            sessionService.setSession(loginForm, ctx());
            if(request().accepts(Http.MimeTypes.TEXT)) {
                return redirect(controllers.admin.routes.HomeController.index());
            }
            return ok("Ok");
        } else {
            loginData.reject("Wrong E-Mail or Password");
            if(request().accepts(Http.MimeTypes.TEXT)) {
                return badRequest(views.html.sessionController.login.render(loginData));
            }
            return badRequest(loginData.errorsAsJson());

        }
    }

    public Result logout() {
        this.sessionService.clear(ctx());
        if(request().accepts(Http.MimeTypes.TEXT)) {
            redirect(controllers.admin.routes.SessionController.login());
        }
        return ok();
    }
}
