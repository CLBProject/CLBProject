package clb.business.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.json.JSONObject;

public class UsersystemObject implements ClbObject, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String address;
    private String name;
    private String password;
    private String id;
    private String token;
    private Date expiryDate;
    private Date lastSentEmail;
    private boolean enabled;
    private String ftpPassword;

    private List<BuildingObject> buildings;
    
    public UsersystemObject(){
        
    }
    
    public boolean hasBuildings() {
    	return buildings != null && buildings.size() > 0;
    }
    
    public boolean emailSentItHasntAlreadyPassedEnoughTimeSince(Date currentDateMinusHours) {
        
        if(getLastSentEmail() == null)
            return false;
        
        return getLastSentEmail().getTime() - currentDateMinusHours.getTime() > 0;
    }
    
    public boolean hasExpiredDate(Date currentDate) {
        
        if(getExpiryDate() == null)
            return false;
        
        return getExpiryDate().getTime() - currentDate.getTime() <= 0;
    }
    
    public JSONObject toJson() {
    	JSONObject json = new JSONObject();
    	json.append("username", this.id);
    	json.append("ftpPassword", this.ftpPassword);
    	return json;
    }
    
    public void addBuilding(BuildingObject buildingObject) {
    	if(buildings == null) {
    		buildings = new ArrayList<BuildingObject>();
    	}
    	
    	buildings.add(buildingObject);
    }

	public void removeBuilding(BuildingObject building) {
		buildings.remove(building);
	}
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((buildings == null) ? 0 : buildings.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((ftpPassword == null) ? 0 : ftpPassword.hashCode());
		result = prime * result + ((lastSentEmail == null) ? 0 : lastSentEmail.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersystemObject other = (UsersystemObject) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (buildings == null) {
			if (other.buildings != null)
				return false;
		} else if (!buildings.equals(other.buildings))
			return false;
		if (enabled != other.enabled)
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (ftpPassword == null) {
			if (other.ftpPassword != null)
				return false;
		} else if (!ftpPassword.equals(other.ftpPassword))
			return false;
		if (lastSentEmail == null) {
			if (other.lastSentEmail != null)
				return false;
		} else if (!lastSentEmail.equals(other.lastSentEmail))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<BuildingObject> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<BuildingObject> buildings) {
		this.buildings = buildings;
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
