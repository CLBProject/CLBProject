package clb.business.objects;

import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.BuildingEntity;

public class BuildingObject
{
    private long buildingid;

    private String name;

    private UsersystemObject usersystem;

    private List<DataLoggerObject> dataLoggers;
    
    public BuildingObject(){
        
    }
    
    public BuildingObject( BuildingEntity building ) {
        this.buildingid = building.getBuildingid();
        this.name = building.getName();
        this.usersystem = new UsersystemObject(building.getUsersystem());
        this.dataLoggers = building.getDataLoggers() != null ?
        		building.getDataLoggers().stream().map( DataLoggerObject::new ).collect( Collectors.toList() ) : null;
    }

    public BuildingEntity toEntity() {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setBuildingid( this.buildingid );
        buildingEntity.setName( this.name );
        buildingEntity.setUsersystem( this.usersystem.toEntity() );
        buildingEntity.setDataLoggers( this.dataLoggers != null ?
        		this.dataLoggers.stream().map( DataLoggerObject::toEntity ).collect( Collectors.toList() ) : null);
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

    public List<DataLoggerObject> getDataLoggers() {
        return dataLoggers;
    }

    public void setDataLoggers( List<DataLoggerObject> dataLoggers ) {
        this.dataLoggers = dataLoggers;
    }

    
}
