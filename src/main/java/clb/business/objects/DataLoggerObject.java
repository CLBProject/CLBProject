package clb.business.objects;

import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.DataLoggerEntity;

public class DataLoggerObject
{

    private long dataloggerid;

    private String name;

    private List<AnalyzerObject> analyzers;

    private BuildingObject building;

    public DataLoggerObject(){
        
    }

    public DataLoggerObject( DataLoggerEntity dataLogger ) {
        this.dataloggerid = dataLogger.getDataloggerid();
        this.name = dataLogger.getName();
        this.building = new BuildingObject(dataLogger.getBuilding());
        this.analyzers = dataLogger.getAnalyzers().stream().map( AnalyzerObject::new ).collect( Collectors.toList() );
    }
    
    public DataLoggerEntity toEntity() {
        DataLoggerEntity dataLogEntity = new DataLoggerEntity();
        dataLogEntity.setDataloggerid( this.dataloggerid );
        dataLogEntity.setBuilding( this.building.toEntity() );
        dataLogEntity.setAnalyzers( this.analyzers.stream().map( AnalyzerObject::toEntity ).collect(Collectors.toList()) );
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


    public List<AnalyzerObject> getAnalyzers() {
        return analyzers;
    }


    public void setAnalyzers( List<AnalyzerObject> analyzers ) {
        this.analyzers = analyzers;
    }


    public BuildingObject getBuilding() {
        return building;
    }


    public void setBuilding( BuildingObject building ) {
        this.building = building;
    }
 
    
}
