package clb.ui.beans.objects;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import clb.business.objects.BuildingObject;

public class BuildingGui {

	@NotNull
	private String buildingid;

    @NotNull(message="Name can't be empty")
    @NotEmpty
	private String name;
    
    private String location;
    
    private String imgPath;
    
    private Set<DivisionGui> divisions;

	public BuildingGui(BuildingObject bObject) {
		super();
		this.buildingid = bObject.getId();
		this.name = bObject.getName();
		this.imgPath = bObject.getImgPath();
		this.location = bObject.getLocation();
		this.divisions = bObject.getDivisions() != null ?
									bObject.getDivisions().stream().map(DivisionGui::new).collect(Collectors.toSet()) :
										null;
	}
	
	public BuildingObject toObject() {
		BuildingObject bobj = new BuildingObject();
		
		bobj.setId(this.buildingid);
		bobj.setName(this.name);
		bobj.setLocation(this.location);
		bobj.setImgPath(this.imgPath);
		bobj.setDivisions(this.divisions != null ? 
									this.divisions.stream().map(DivisionGui::toObject).collect(Collectors.toSet()) : 
										null);
		
		return bobj;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildingGui other = (BuildingGui) obj;
		if (buildingid == null) {
			if (other.buildingid != null)
				return false;
		} else if (!buildingid.equals(other.buildingid))
			return false;
		return true;
	}

	public boolean hasDivisions() {
		return divisions != null && divisions.size() > 0;
	}
	
	public void addDivision(DivisionGui divisionG) {
		if(divisions == null) {
			divisions = new HashSet<DivisionGui>();
		}
		
		divisions.add(divisionG);
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
	
	public Set<DivisionGui> getDivisions() {
		return divisions;
	}

	public void setDivisions(Set<DivisionGui> divisions) {
		this.divisions = divisions;
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
