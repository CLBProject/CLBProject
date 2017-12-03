package clb.business.objects;

import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.UsersystemEntity;

public class UsersystemObject
{
    
    private String userid;
    private String address;
    private String name;
    private String password;
    private String username;
    private List<BuildingObject> buildings;

    public UsersystemObject(){
        
    }
    
    public UsersystemObject( UsersystemEntity usersystem ) {
        this.userid = usersystem.getUserid();
        this.address = usersystem.getAddress();
        this.name = usersystem.getName();
        this.password = usersystem.getPassword();
        this.buildings = usersystem.getBuildings().stream().map( BuildingObject::new ).collect( Collectors.toList() );
    }

    public UsersystemEntity toEntity() {
        UsersystemEntity userSystemEntity = new UsersystemEntity();
        userSystemEntity.setUserid( this.userid );
        userSystemEntity.setAddress( this.address );
        userSystemEntity.setName( this.name );
        userSystemEntity.setPassword( this.password );
        userSystemEntity.setUsername( this.username );
        userSystemEntity.setBuildings( this.buildings.stream().map( BuildingObject::toEntity ).collect( Collectors.toList() ) );
        
        return userSystemEntity;
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

    public void setBuildings( List<BuildingObject> buildings ) {
        this.buildings = buildings;
    }
    
    

}