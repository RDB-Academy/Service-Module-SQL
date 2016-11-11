package form;

import play.data.validation.Constraints;

/**
 * Created by nicolenaczk on 03.11.16.
 */
public class LoginForm {

    @Constraints.Required
    protected String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}

