package clb.ui.beans.newobjects;

import java.util.HashSet;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;

public class BuildingNewManagementGui {

	@NotNull
	private String buildingid;

    @NotNull(message="Name can't be empty")
    @NotEmpty
	private String name;
    
    private String location;
    
    private String imgPath;

	public BuildingNewManagementGui() {
	}
	
	public BuildingObject toObject() {
		BuildingObject bobj = new BuildingObject();
		
		bobj.setId(this.buildingid);
		bobj.setName(this.name);
		bobj.setLocation(this.location);
		bobj.setImgPath(this.imgPath);
		bobj.setDivisions(new HashSet<DivisionObject>());
		
		return bobj;
	}


	public String getBuildingid() {
		return buildingid;
	}

	public void setBuildingid(String buildingid) {
		this.buildingid = buildingid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}