package clb.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * The persistent class for the USERSYSTEM database table.
 * 
 */
@Document(collection="Users")
public class UsersystemEntity implements ClbEntity, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String userid;

	private String address;

	private String name;

	private String password;

	private String username;
	
	private String token;
	
	private Date expiryDate;
	
	private Date lastSentEmail;
	
	private boolean enabled;
	
	@DBRef
	private List<BuildingEntity> buildings;

	public UsersystemEntity() {
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public List<BuildingEntity> getBuildings() {
        return buildings;
    }

    public void setBuildings( List<BuildingEntity> buildings ) {
        this.buildings = buildings;
    }
	
    public void addBuilding(BuildingEntity building) {
    	if(buildings == null) {
    		buildings = new ArrayList<BuildingEntity>();
    	}
    	
    	buildings.add(building);
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

    public Date getLastSentEmail() {
        return lastSentEmail;
    }

    public void setLastSentEmail( Date lastSentEmail ) {
        this.lastSentEmail = lastSentEmail;
    }
	
    
}