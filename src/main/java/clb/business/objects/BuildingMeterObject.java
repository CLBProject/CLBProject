package clb.business.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.BuildingMeterEntity;

public class BuildingMeterObject
{
    private String buildingMeterId;

    private String name;

    private List<BuildingMeterParameterObject> buildingMeterParameters;
    
    public BuildingMeterObject(){
        
    }
    
    public BuildingMeterObject( BuildingMeterEntity buildingMeterEntity ) {
        this.buildingMeterId = buildingMeterEntity.getBuildingMeterId();
        this.name = buildingMeterEntity.getName();
        this.buildingMeterParameters = buildingMeterEntity.getBuildingMeterParameters() != null ?
                buildingMeterEntity.getBuildingMeterParameters().stream().map(BuildingMeterParameterObject::new).collect( Collectors.toList()) : null;
    }

    public BuildingMeterEntity toEntity() {
        BuildingMeterEntity buildingMeterEntity = new BuildingMeterEntity();
        buildingMeterEntity.setBuildingMeterId( this.buildingMeterId );
        buildingMeterEntity.setName( this.name );
        buildingMeterEntity.setBuildingMeterParameters( this.buildingMeterParameters != null ?
                this.buildingMeterParameters.stream().map(BuildingMeterParameterObject::toEntity).collect(Collectors.toList()) : null);
        
        return buildingMeterEntity;
    }

    public void addBuildingMeterParameter(BuildingMeterParameterObject buildingMeterParameterObj) {
        if(buildingMeterParameters == null) {
            buildingMeterParameters = new ArrayList<BuildingMeterParameterObject>();
        }
        
        buildingMeterParameters.add(buildingMeterParameterObj);
    }
    
    public String getBuildingMeterId() {
        return buildingMeterId;
    }

    public void setBuildingMeterId( String buildingMeterId ) {
        this.buildingMeterId = buildingMeterId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<BuildingMeterParameterObject> getBuildingMeterParameters() {
        return buildingMeterParameters;
    }

    public void setBuildingMeterParameters( List<BuildingMeterParameterObject> buildingMeterParameters ) {
        this.buildingMeterParameters = buildingMeterParameters;
    }
    
    
    
}
