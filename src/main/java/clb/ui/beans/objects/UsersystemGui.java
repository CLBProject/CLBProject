package clb.ui.beans.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import clb.business.objects.UsersystemObject;

public class UsersystemGui implements Serializable
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
    private Date lastSentEmail;
    private boolean enabled;
    
    private List<BuildingGui> buildings;
    
    public UsersystemGui() {
    	
    }
    
    public UsersystemGui(UsersystemObject userObject) {
    	this.address = userObject.getAddress();
    	this.name = userObject.getName();
    	this.password = userObject.getPassword();
    	this.username = userObject.getUsername();
    	
    	this.token = userObject.getToken();
    	this.expiryDate = userObject.getExpiryDate();
    	this.lastSentEmail = userObject.getLastSentEmail();
    	this.enabled = userObject.isEnabled();
    	
    	this.buildings = userObject.getBuildings() != null ? 
    							userObject.getBuildings().stream().map(BuildingGui::new).collect(Collectors.toList()) : 
    								null;
    }
 
    public void clear() {
    	this.username = null;
    	this.address = null;
    	this.name = null;
    	this.password = null;
    	this.token = null;
    	this.expiryDate = null;
    	this.lastSentEmail = null;
    	this.enabled = false;
    }

    public UsersystemObject toObject() {
    	UsersystemObject userObj = new UsersystemObject();
    	userObj.setUsername(this.username);
    	userObj.setAddress(this.address);
    	userObj.setName(this.name);
    	userObj.setPassword(this.password);
    	userObj.setToken(this.token);
    	userObj.setExpiryDate(this.expiryDate);
    	userObj.setLastSentEmail(this.lastSentEmail);
    	userObj.setEnabled(this.enabled);
    	userObj.setBuildings(this.buildings != null ? this.buildings.stream().map(BuildingGui::toObject).collect(Collectors.toList()) : null);
    	return userObj;
    }
    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getLastSentEmail() {
		return lastSentEmail;
	}

	public void setLastSentEmail(Date lastSentEmail) {
		this.lastSentEmail = lastSentEmail;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void addBuilding(BuildingGui building) {
    	if(buildings == null) {
    		buildings = new ArrayList<BuildingGui>();
    	}
    	
    	buildings.add(building);
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

	public List<BuildingGui> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<BuildingGui> buildings) {
		this.buildings = buildings;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean hasBuildings() {
		return buildings != null && buildings.size() > 0;
	}

    
}



