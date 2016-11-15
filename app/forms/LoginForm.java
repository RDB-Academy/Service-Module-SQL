package forms;

import play.data.validation.Constraints;

/**
 * @author nicolenaczk
 */
public class LoginForm {

    @Constraints.Email
    @Constraints.Required
    private String email;

    @Constraints.Required
    private String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

