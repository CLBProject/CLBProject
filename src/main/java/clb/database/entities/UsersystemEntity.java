package clb.database.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * The persistent class for the USERSYSTEM database table.
 * 
 */
@Document(collection="UsersSystem")
public class UsersystemEntity implements ClbEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String address;

	private String name;

	private String password;
	
	private String token;
	
	private Date expiryDate;
	
	private Date lastSentEmail;
	
	private boolean enabled;
	
	private String ftpPassword;
	
	@DBRef
	private Set<BuildingEntity> buildings;

	public UsersystemEntity() {
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

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<BuildingEntity> getBuildings() {
        return buildings;
    }

    public void setBuildings( Set<BuildingEntity> buildings ) {
        this.buildings = buildings;
    }
	
    public void addBuilding(BuildingEntity building) {
    	if(buildings == null) {
    		buildings = new HashSet<BuildingEntity>();
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

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	
    
}