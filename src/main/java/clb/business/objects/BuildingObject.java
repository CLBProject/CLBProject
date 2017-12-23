package clb.business.objects;

import clb.database.entities.BuildingEntity;

public class BuildingObject
{
    private long buildingid;

    private String name;

    private UsersystemObject usersystem;

    public BuildingObject(){
        
    }
    
    public BuildingObject( BuildingEntity building ) {
        this.buildingid = building.getBuildingid();
        this.name = building.getName();
        this.usersystem = building.getUsersystem() != null ? new UsersystemObject(building.getUsersystem()) : new UsersystemObject();
    }

    public BuildingEntity toEntity() {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setBuildingid( this.buildingid );
        buildingEntity.setName( this.name );
        buildingEntity.setUsersystem( this.usersystem != null ? this.usersystem.toEntity() : null );

        return buildingEntity;
    }

    public long getBuildingid() {
        return buildingid;
    }

    public void setBuildingid( long buildingid ) {
        this.buildingid = buildingid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public UsersystemObject getUsersystem() {
        return usersystem;
    }

    public void setUsersystem( UsersystemObject usersystem ) {
        this.usersystem = usersystem;
    }
}
