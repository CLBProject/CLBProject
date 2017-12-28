package clb.business.objects;

import clb.database.entities.BuildingEntity;

public class BuildingObject
{
    private long buildingid;

    private String name;

    public BuildingObject(){
        
    }
    
    public BuildingObject( BuildingEntity building ) {
        this.buildingid = building.getBuildingid();
        this.name = building.getName();
    }

    public BuildingEntity toEntity() {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setBuildingid( this.buildingid );
        buildingEntity.setName( this.name );

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
}
