package clb.business.objects;

import clb.database.entities.BuildingEntity;

public class BuildingObject
{
    private String buildingid;

    private String name;

    private String buildingusername;

    private String location;
    
    private String imgPath;

    private DivisionObject mainDivision;

    public BuildingObject(){

    }

    public BuildingObject( BuildingEntity building ) {
        this.buildingid = building.getBuildingid();
        this.name = building.getName();
        this.buildingusername = building.getBuildingusername();
        this.location = building.getLocation();
        this.imgPath = building.getImgPath();
        this.mainDivision = building.getMainDivision() != null ? new DivisionObject(building.getMainDivision()) : null;     
    }

    public BuildingEntity toEntity() {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setBuildingid( this.buildingid );
        buildingEntity.setName( this.name );
        buildingEntity.setLocation(this.location);
        buildingEntity.setImgPath( this.imgPath );
        buildingEntity.setMainDivision(this.mainDivision != null ? this.mainDivision.toEntity() : null);

        return buildingEntity;
    }

    public String getBuildingid() {
        return buildingid;
    }

    public void setBuildingid( String buildingid ) {
        this.buildingid = buildingid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getBuildingusername() {
        return buildingusername;
    }

    public void setBuildingusername(String buildingusername) {
        this.buildingusername = buildingusername;
    }

	public String getImgPath() {
        return imgPath;
    }

    public void setImgPath( String imgPath ) {
        this.imgPath = imgPath;
    }

	public DivisionObject getMainDivision() {
		return mainDivision;
	}

	public void setMainDivision(DivisionObject mainDivision) {
		this.mainDivision = mainDivision;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
    
    
}



