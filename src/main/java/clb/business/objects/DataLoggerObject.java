package clb.business.objects;

import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.DataLoggerEntity;

public class DataLoggerObject
{

    private long dataloggerid;

    private String name;

    private BuildingObject building;

    public DataLoggerObject(){
        
    }

    public DataLoggerObject( DataLoggerEntity dataLogger ) {
        this.dataloggerid = dataLogger.getDataloggerid();
        this.name = dataLogger.getName();
        this.building = dataLogger.getBuilding() != null ? new BuildingObject(dataLogger.getBuilding()) : new BuildingObject();
    }
    
    public DataLoggerEntity toEntity() {
        DataLoggerEntity dataLogEntity = new DataLoggerEntity();
        dataLogEntity.setDataloggerid( this.dataloggerid );
        dataLogEntity.setBuilding( this.building != null ? this.building.toEntity() : null );

        return dataLogEntity;
    }


    public long getDataloggerid() {
        return dataloggerid;
    }


    public void setDataloggerid( long dataloggerid ) {
        this.dataloggerid = dataloggerid;
    }


    public String getName() {
        return name;
    }


    public void setName( String name ) {
        this.name = name;
    }


    public BuildingObject getBuilding() {
        return building;
    }


    public void setBuilding( BuildingObject building ) {
        this.building = building;
    }
 
    
}
