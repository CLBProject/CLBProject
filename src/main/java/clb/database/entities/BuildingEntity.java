package clb.database.entities;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the BuildingMeters database table.
 * 
 */

@Document(collection="Buildings")
public class BuildingEntity implements ClbEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;
	
	private String buildingusername;
	
	private String location;
	
	private String imgPath;

	@DBRef
	private List<DivisionEntity> divisions;

	public BuildingEntity() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getBuildingusername() {
		return this.buildingusername;
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
    

	public List<DivisionEntity> getDivisions() {
		return divisions;
	}

	public void setDivisions(List<DivisionEntity> divisions) {
		this.divisions = divisions;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
    
    
    
}