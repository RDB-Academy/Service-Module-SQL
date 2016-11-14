package services;

import com.google.inject.ImplementedBy;
import forms.LoginForm;
import models.Session;
import play.mvc.Http;
import services.implementation.SessionServiceImplementation;

/**
 * @author fabiomazzone
 */
@ImplementedBy(SessionServiceImplementation.class)
public interface SessionService {
    String SESSION_FIELD_NAME = "SessionID";

    void setSession(LoginForm loginForm, Http.Context ctx);

    Session getSession(Http.Context ctx);

    void clear(Http.Context ctx);
}
