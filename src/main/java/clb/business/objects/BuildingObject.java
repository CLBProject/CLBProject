package clb.business.objects;

import java.util.HashSet;
import java.util.Set;

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



