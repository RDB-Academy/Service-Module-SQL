package form;

import play.data.validation.Constraints;

import javax.validation.Constraint;

/**
 * Created by nicolenaczk on 03.11.16.
 */
public class LoginForm {

    @Constraints.Required
    protected String username;
    protected String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}

