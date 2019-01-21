package clb.ui.beans.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import clb.business.objects.UsersystemObject;

public class UserSystemGui implements Serializable
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
  
    
    private List<BuildingGui> buildings;
    
    public UserSystemGui() {
    	
    }
    
    public UserSystemGui(UsersystemObject userObject) {
    	this.address = userObject.getAddress();
    	this.name = userObject.getName();
    	this.password = userObject.getPassword();
    	this.username = userObject.getUsername();
    	
    	this.buildings = userObject.getBuildings() != null ? 
    							userObject.getBuildings().stream().map(BuildingGui::new).collect(Collectors.toList()) : 
    								null;
    }
 
    public void clear() {
    	this.username = null;
    	this.address = null;
    	this.name = null;
    	this.password = null;
    }

	public UsersystemObject toObject() {
		UsersystemObject userObj = new UsersystemObject();
		userObj.setAddress(this.address);
		userObj.setName(this.name);
		userObj.setPassword(this.password);
		userObj.setBuildings(buildings != null ? buildings.stream().map(BuildingGui::toObject).collect(Collectors.toList()) : null);
		
		return userObj;
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



