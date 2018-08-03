package clb.ui.beans.pojos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import clb.business.objects.UsersystemObject;

public class UserLoginPojo implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @NotNull(message="Password can't be empty")
    @NotEmpty
    private String password;
    
    @NotNull(message="Email can't be empty")
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+", message = "Email format is invalid.")
    private String username;
    
    private UsersystemObject currentUser;
    
    public UserLoginPojo() {	
    }

    public String getPassword() {
        return password;
    }
    public void setPassword( String password ) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername( String username ) {
        this.username = username;
    }

    public UsersystemObject getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser( UsersystemObject currentUser ) {
        this.currentUser = currentUser;
    }
}



