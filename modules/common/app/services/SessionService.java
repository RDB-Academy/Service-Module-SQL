package services;

import eu.bitwalker.useragentutils.UserAgent;
import forms.LoginForm;
import models.Session;
import play.Configuration;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http;
import repository.SessionRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

/**
 * @author fabiomazzone
 */
@Singleton
public class SessionService {
    private static final String SESSION_FIELD_NAME = "auth-key";

    private final SessionRepository sessionRepository;
    private final FormFactory formFactory;
    private final Configuration configuration;

    @Inject
    public SessionService(
            SessionRepository sessionRepository,
            FormFactory formFactory,
            Configuration configuration) {

        this.sessionRepository = sessionRepository;
        this.formFactory = formFactory;
        this.configuration = configuration;
    }

    private Session setAdminSession(Http.Context ctx) {
        Session session = new Session();
        session.save();

        session.setUserId(0L);
        session.setUsername("admin");
        UserAgent userAgent = new UserAgent(ctx.request().getHeader(Http.HeaderNames.USER_AGENT));
        String connectedData = userAgent.toString() + ctx.request().remoteAddress();

        session.setConnectionInfo(connectedData.hashCode());

        session.save();

        return session;
    }

    Session createSession(Http.Context ctx) {
        Session session = new Session();
        UserAgent userAgent = new UserAgent(ctx.request().getHeader(Http.HeaderNames.USER_AGENT));
        String connectedData = userAgent.toString() + ctx.request().remoteAddress();

        session.setConnectionInfo(connectedData.hashCode());

        session.save();

        ctx.session().put(SESSION_FIELD_NAME, session.getId());

        return session;
    }

    public Session getSession(Http.Context ctx) {
        // Old Method
        String sessionId = ctx.session().get(SESSION_FIELD_NAME);

        // new Method
        String[] authKey = ctx.request().headers().get(SESSION_FIELD_NAME);
        if(authKey != null && authKey.length == 1 && !authKey[0].isEmpty()) {
            sessionId = authKey[0];
        }

        if(sessionId == null || sessionId.isEmpty()) {
            return null;
        }

        Session session = sessionRepository.getById(sessionId);

        if (session == null) {
            return null;
        }

        return session;
    }

    public Form<LoginForm> validateLoginForm() {
        Form<LoginForm>     loginForm;
        String              adminPassword;
        LoginForm           login;

        // Log Body
        System.out.println(Http.Context.current().request().body().asJson().toString());

        loginForm       = this.getLoginForm().bindFromRequest();
        adminPassword   = this.configuration.getString("sqlModule.adminPassword");

        if (loginForm.hasErrors()) {
            return loginForm;
        }

        login = loginForm.get();

        if (!login.getPassword().equals(adminPassword)) {
            loginForm.reject("Wrong E-Mail or Password");
            return loginForm;
        }

        return loginForm;
    }

    public Session login(Form<LoginForm> loginForm) {
        return setAdminSession(Http.Context.current());
    }

    public boolean logout() {
        Http.Context.current().session().clear();
        return true;
    }

    public Form<LoginForm> getLoginForm() {
        return this.formFactory.form(LoginForm.class);
    }

    /**
     * This function Checks if the Session contains a UserName
     * @param ctx the current Http.Context
     * @return returns true if this is a authenticated Session
     */
    public boolean isLoggedIn(@NotNull Http.Context ctx) {
        Session session = this.getSession(ctx);
        return session != null && !(session.getUsername() == null || session.getUsername().isEmpty());
    }

    public void save(Session session) {
        session.save();
    }
}
