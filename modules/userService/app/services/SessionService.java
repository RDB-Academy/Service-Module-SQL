package services;

import eu.bitwalker.useragentutils.UserAgent;
import forms.LoginForm;
import models.Session;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http;
import repositories.SessionRepository;
import com.typesafe.config.Config;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author fabiomazzone
 */
@Singleton
public class SessionService
{
    public static final String SESSION_FIELD_NAME = "auth-key";

    private final SessionRepository sessionRepository;
    private final FormFactory formFactory;
    private final Config config;

    @Inject
    public SessionService(
            SessionRepository sessionRepository,
            FormFactory formFactory,
            Config config)
    {
        this.sessionRepository = sessionRepository;
        this.formFactory = formFactory;
        this.config = config;
    }

    private Session setAdminSession(Http.Context context) {
        Session             session = new Session();

        sessionRepository.save(session);

        //session.setUserId(0L);
        //session.setUsername("admin");

        //session.setConnectionInfo(getUserAgent(context.request()).hashCode());

        sessionRepository.save(session);

        return session;
    }

    public Optional<Session> createSession(Http.Context context, Form<LoginForm> loginForm)
    {
        Session session = new Session();

        // session.setConnectionInfo(getUserAgent(context.request()).hashCode());
        sessionRepository.save(session);
        context.response().setHeader(SESSION_FIELD_NAME, session.getId());

        return Optional.ofNullable(session);
    }

    /**
     * returns a active session object selected by the given session Id
     *
     * @param sessionId the session identifier
     * @return returns the session object if the session is active , else it returns null
     */
    public Session findActiveSessionById(@NotNull String sessionId)
    {
        Session session = this.sessionRepository.getById(sessionId);
        if (session != null && this.isActive(session))
        {
            return session;
        }
        return null;
    }

    private String getUserAgent(Http.Request request) {
        Optional<String>        headerUserAgent;
        Optional<UserAgent>     parsedUserAgent;
        String                  formattedUserAgent;

        headerUserAgent = request.header(Http.HeaderNames.USER_AGENT);
        parsedUserAgent = headerUserAgent.map(UserAgent::new);
        formattedUserAgent = parsedUserAgent.map(UserAgent::toString).orElse("");

        return formattedUserAgent;
    }

    public Form<LoginForm> validateLoginForm()
    {
        Form<LoginForm> loginForm;
        String adminPassword;
        LoginForm login;

        loginForm = this.getLoginForm().bindFromRequest();
        adminPassword = this.config.getString("sqlModule.adminPassword");

        if (loginForm.hasErrors())
        {
            return loginForm;
        }

        login = loginForm.get();

        if (!login.getPassword().equals(adminPassword))
        {
            loginForm.withGlobalError("Wrong E-Mail or Password");
            return loginForm;
        }

        return loginForm;
    }

    public Session login(Form<LoginForm> loginForm)
    {
        Session session = this.setAdminSession(Http.Context.current());
        Http.Context.current().response().setHeader(SESSION_FIELD_NAME, session.getId());
        return session;
    }

    private Form<LoginForm> getLoginForm()
    {
        return this.formFactory.form(LoginForm.class);
    }

    /**
     * This function Checks if the Session contains a UserName
     *
     * @param session the session object
     * @return returns true if this is a authenticated Session
     */
    public boolean isActive(@NotNull Session session)
    {
        return session.getId() != null && !session.getId().isEmpty();
    }

    /**
     * this functions checks if the current session is an admin-session
     *
     * @param session a active session object
     * @return returns true if is admin or false
     */
    public boolean isAdmin(@NotNull Session session)
    {
        // ToDo
        return true; // isActive(session) && session.getId().equals("admin");
    }

    public void invalidate(Session session)
    {
        // ToDo
    }
}
