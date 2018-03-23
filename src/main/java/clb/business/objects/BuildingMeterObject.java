package clb.business.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.BuildingMeterEntity;

public class BuildingMeterObject
{
    private String buildingMeterId;

    private String name;
    
    private String labelKey;

    private List<BuildingMeterObject> buildingMeterParameters;
    
    public BuildingMeterObject(){
        
    }
    
    public BuildingMeterObject( BuildingMeterEntity buildingMeterEntity ) {
        this.buildingMeterId = buildingMeterEntity.getBuildingMeterId();
        this.name = buildingMeterEntity.getName();
        this.labelKey = buildingMeterEntity.getLabelKey();
        this.buildingMeterParameters = buildingMeterEntity.getBuildingMeterParameters() != null ?
                buildingMeterEntity.getBuildingMeterParameters().stream().map(BuildingMeterObject::new).collect( Collectors.toList()) : null;
    }

    public BuildingMeterEntity toEntity() {
        BuildingMeterEntity buildingMeterEntity = new BuildingMeterEntity();
        buildingMeterEntity.setBuildingMeterId( this.buildingMeterId );
        buildingMeterEntity.setName( this.name );
        buildingMeterEntity.setLabelKey( this.labelKey );
        buildingMeterEntity.setBuildingMeterParameters( this.buildingMeterParameters != null ?
                this.buildingMeterParameters.stream().map(BuildingMeterObject::toEntity).collect(Collectors.toList()) : null);
        
        return buildingMeterEntity;
    }

    public void addBuildingMeterParameter(BuildingMeterObject buildingMeterParameterObj) {
        if(buildingMeterParameters == null) {
            buildingMeterParameters = new ArrayList<BuildingMeterObject>();
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

    public List<BuildingMeterObject> getBuildingMeterParameters() {
        return buildingMeterParameters;
    }

    public void setBuildingMeterParameters( List<BuildingMeterObject> buildingMeterParameters ) {
        this.buildingMeterParameters = buildingMeterParameters;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey( String labelKey ) {
        this.labelKey = labelKey;
    }
    
    
    
}
