package clb.business.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.BuildingEntity;

public class BuildingObject implements ClbObject
{
    private String id;

    private String name;

    private String buildingusername;

    private String location;
    
    private String imgPath;

    private List<DivisionObject> divisions;

    public BuildingObject(){

    }

    public BuildingObject( BuildingEntity building ) {
        this.id = building.getId();
        this.name = building.getName();
        this.buildingusername = building.getBuildingusername();
        this.location = building.getLocation();
        this.imgPath = building.getImgPath();
        this.divisions = building.getDivisions() != null ? 
        					building.getDivisions().stream().map(DivisionObject::new).collect(Collectors.toList()) : 
        						null;     
    }

    public BuildingEntity toEntity() {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setId( this.id );
        buildingEntity.setName( this.name );
        buildingEntity.setLocation(this.location);
        buildingEntity.setImgPath( this.imgPath );
        buildingEntity.setDivisions(this.divisions != null ? 
        								this.divisions.stream().map(DivisionObject::toEntity).collect(Collectors.toList()): 
        									null);

        return buildingEntity;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((buildingusername == null) ? 0 : buildingusername.hashCode());
		result = prime * result + ((divisions == null) ? 0 : divisions.hashCode());
		result = prime * result + ((imgPath == null) ? 0 : imgPath.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (buildingusername == null) {
			if (other.buildingusername != null)
				return false;
		} else if (!buildingusername.equals(other.buildingusername))
			return false;
		if (divisions == null) {
			if (other.divisions != null)
				return false;
		} else if (!divisions.equals(other.divisions))
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public void addDivision(DivisionObject divObj) {
		if(this.divisions == null) {
			this.divisions = new ArrayList<DivisionObject>();
		}
		
		this.divisions.add(divObj);
	}
   
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

    
	public List<DivisionObject> getDivisions() {
		return divisions;
	}

	public void setDivisions(List<DivisionObject> divisions) {
		this.divisions = divisions;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


}



