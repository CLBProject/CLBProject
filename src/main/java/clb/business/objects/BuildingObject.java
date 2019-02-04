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

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildingid == null) ? 0 : buildingid.hashCode());
		result = prime * result + ((buildingusername == null) ? 0 : buildingusername.hashCode());
		result = prime * result + ((imgPath == null) ? 0 : imgPath.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((mainDivision == null) ? 0 : mainDivision.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildingObject other = (BuildingObject) obj;
		if (buildingid == null) {
			if (other.buildingid != null)
				return false;
		} else if (!buildingid.equals(other.buildingid))
			return false;
		if (buildingusername == null) {
			if (other.buildingusername != null)
				return false;
		} else if (!buildingusername.equals(other.buildingusername))
			return false;
		if (imgPath == null) {
			if (other.imgPath != null)
				return false;
		} else if (!imgPath.equals(other.imgPath))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (mainDivision == null) {
			if (other.mainDivision != null)
				return false;
		} else if (!mainDivision.equals(other.mainDivision))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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



