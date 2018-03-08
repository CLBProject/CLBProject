package clb.business.objects;

import clb.database.entities.BuildingMeterParameterEntity;

public class BuildingMeterParameterObject
{
    private String buildingMeterParameterId;

    private String name;

    public BuildingMeterParameterObject(){  
    }
    
    public BuildingMeterParameterObject( BuildingMeterParameterEntity buildingMeterParameterEntity ) {
        this.buildingMeterParameterId = buildingMeterParameterEntity.getBuildingMeterParameterId();
        this.name = buildingMeterParameterEntity.getName();
    }

    public BuildingMeterParameterEntity toEntity() {
        BuildingMeterParameterEntity buildingMeterEntity = new BuildingMeterParameterEntity();
        buildingMeterEntity.setBuildingMeterParameterId( buildingMeterParameterId );
        buildingMeterEntity.setName( this.name );

        return buildingMeterEntity;
    }

    public String getBuildingMeterParameterId() {
        return buildingMeterParameterId;
    }

    public void setBuildingMeterParameterId( String buildingMeterParameterId ) {
        this.buildingMeterParameterId = buildingMeterParameterId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
 
}
