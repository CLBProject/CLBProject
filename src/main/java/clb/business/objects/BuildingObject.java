package clb.business.objects;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import clb.database.entities.BuildingEntity;

public class BuildingObject implements ClbObject
{
    private String id;

    private String name;

    private String buildingusername;

    private String location;
    
    private String imgPath;

    private Set<DivisionObject> divisions;

    public BuildingObject(){

    }

    public BuildingObject( BuildingEntity building ) {
        this.id = building.getId();
        this.name = building.getName();
        this.buildingusername = building.getBuildingusername();
        this.location = building.getLocation();
        this.imgPath = building.getImgPath();
        this.divisions = building.getDivisions() != null ? 
        					building.getDivisions().stream().map(DivisionObject::new).collect(Collectors.toSet()) : 
        						null;     
    }

    public BuildingEntity toEntity() {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setId( this.id );
        buildingEntity.setName( this.name );
        buildingEntity.setLocation(this.location);
        buildingEntity.setImgPath( this.imgPath );
        buildingEntity.setDivisions(this.divisions != null ? 
        								this.divisions.stream().map(DivisionObject::toEntity).collect(Collectors.toSet()): 
        									null);

        return buildingEntity;
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
		return true;
	}

	public void addDivision(DivisionObject divObj) {
		if(this.divisions == null) {
			this.divisions = new HashSet<DivisionObject>();
		}
		
		this.divisions.add(divObj);
	}
   
	public void deleteDivision(DivisionObject divisionChildObj) {
		divisions.remove(divisionChildObj);
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

    
	public Set<DivisionObject> getDivisions() {
		return divisions;
	}

	public void setDivisions(Set<DivisionObject> divisions) {
		this.divisions = divisions;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}



