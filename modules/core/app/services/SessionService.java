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

        ctx.response().setHeader(SESSION_FIELD_NAME, session.getId());

        return session;
    }

    /**
     * returns a session object to the given request
     *
     * @param request the request
     * @return returns the session object if a valid session matches the request, else returns null
     */
    public Session getSession(@NotNull Http.Request request) {
        Session session;
        String sessionKey = request.getHeader(SESSION_FIELD_NAME);

        // Check Session Key
        if (sessionKey == null || sessionKey.isEmpty()) {
            return null;
        }

        // Get Session by Key & check a session was found
        session = this.sessionRepository.getById(sessionKey);
        if (session == null || !session.isValid()) {
            return null;
        }

        return session;
    }

    public Form<LoginForm> validateLoginForm() {
        Form<LoginForm> loginForm;
        String adminPassword;
        LoginForm login;

        loginForm = this.getLoginForm().bindFromRequest();
        adminPassword = this.configuration.getString("sqlModule.adminPassword");

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
        Session session = this.setAdminSession(Http.Context.current());
        Http.Context.current().response().setHeader(SESSION_FIELD_NAME, session.getId());
        return session;
    }

    private Form<LoginForm> getLoginForm() {
        return this.formFactory.form(LoginForm.class);
    }

    /**
     * This function Checks if the Session contains a UserName
     *
     * @param ctx the current Http.Context
     * @return returns true if this is a authenticated Session
     */
    public boolean isLoggedIn(@NotNull Http.Context ctx) {
        Session session = this.getSession(ctx.request());
        return session != null && !(session.getUsername() == null || session.getUsername().isEmpty());
    }

    /**
     * this functions checks if the current session is an admin-session
     *
     * @param session a valid session object
     * @return returns true if is admin or false
     */
    public boolean isAdmin(@NotNull Session session) {
        // ToDo
        return session.getUsername() != null && session.getUsername().equals("admin");
    }

    public void save(Session session) {
        session.save();
    }

    public boolean logout() {
        Session session = this.getSession(Http.Context.current().request());

        //session.invalidate();

        return true;
    }

    
}
