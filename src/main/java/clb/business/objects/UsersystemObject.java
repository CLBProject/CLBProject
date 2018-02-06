package clb.business.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.UsersystemEntity;

public class UsersystemObject implements ClbObject
{
    private String userid;
    private String address;
    private String name;
    private String password;
    private String username;
    private String token;
    private Date expiryDate;
    private boolean enabled;

    private List<BuildingObject> buildings;
    
    public UsersystemObject(){
        
    }
    
    public UsersystemObject( UsersystemEntity usersystem ) {
        this.userid = usersystem.getUserid();
        this.address = usersystem.getAddress();
        this.name = usersystem.getName();
        this.password = usersystem.getPassword();
        this.token = usersystem.getToken();
        this.expiryDate = usersystem.getExpiryDate();
        this.enabled = usersystem.isEnabled();
        this.buildings = usersystem.getBuildings() != null ? 
        		usersystem.getBuildings().stream().map(BuildingObject::new).collect(Collectors.toList()) : null;
    }

    public UsersystemEntity toEntity() {
        UsersystemEntity userSystemEntity = new UsersystemEntity();
        userSystemEntity.setUserid( this.userid );
        userSystemEntity.setAddress( this.address );
        userSystemEntity.setName( this.name );
        userSystemEntity.setPassword( this.password );
        userSystemEntity.setUsername( this.username );
        userSystemEntity.setToken( this.token );
        userSystemEntity.setExpiryDate( this.expiryDate );
        userSystemEntity.setEnabled( this.enabled );
        userSystemEntity.setBuildings( this.buildings != null ?
        		this.buildings.stream().map(BuildingObject::toEntity).collect(Collectors.toList()) : null);
        
        return userSystemEntity;
    }
    
    public void addBuilding(BuildingObject buildingObject) {
    	if(buildings == null) {
    		buildings = new ArrayList<BuildingObject>();
    	}
    	
    	buildings.add(buildingObject);
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid( String userid ) {
        this.userid = userid;
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
    
    
}
