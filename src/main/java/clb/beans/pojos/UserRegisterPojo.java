package clb.beans.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import clb.business.objects.UsersystemObject;

public class UserRegisterPojo implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String address;
    
    @NotNull(message="Name can't be empty")
    @NotEmpty
    private String name;
    
    @NotNull(message="Password can't be empty")
    @NotEmpty
    private String password;
    
    @NotNull(message="Email can't be empty")
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+", message = "Email format is invalid.")
    private String username;
    
    private String token;
    
    private Date expiryDate;
    
    private boolean enabled;
    
    public UserRegisterPojo() {
    	
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress( String address ) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
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
    public String getToken() {
        return token;
    }
    public void setToken( String token ) {
        this.token = token;
    }
    public Date getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate( Date expiryDate ) {
        this.expiryDate = expiryDate;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }
    
    
    public UsersystemObject toObject() {
        UsersystemObject userObj = new UsersystemObject();
        userObj.setAddress(this.address);
        userObj.setName(this.name);
        userObj.setUsername(this.username);
        userObj.setPassword(this.password);
        userObj.setUserid(this.username);
        userObj.setToken( this.token );
        userObj.setEnabled( this.enabled );
        userObj.setExpiryDate( this.expiryDate );
        
        return userObj;
    }
}



