package clb.beans.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import clb.business.objects.UsersystemObject;

public class UsersystemPojo
{
    private String address;
    
    @NotNull(message="Name can't be empty")
    private String name;
    
    @NotNull(message="Password can't be empty")
    private String password;
    
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+", message = "Email format is invalid.")
    private String username;
    
    public UsersystemPojo() {
    	
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
    
    public UsersystemObject toObject() {
    	UsersystemObject userObj = new UsersystemObject();
    	userObj.setAddress(this.address);
    	userObj.setName(this.name);
    	userObj.setUsername(this.username);
    	userObj.setPassword(this.password);
    	userObj.setUserid(this.username);
    	
    	return userObj;
    }
}
