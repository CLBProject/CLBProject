package clb.ui.beans.objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import clb.business.objects.BuildingObject;

public class BuildingGui {

	@NotNull
	private String buildingid;

    @NotNull(message="Name can't be empty")
    @NotEmpty
	private String name;
    
    private String imgPath;
    
    private DivisionGui mainDivision;

	public BuildingGui() {
	}

	public BuildingGui(BuildingObject bObject) {
		super();
		this.buildingid = bObject.getBuildingid();
		this.name = bObject.getName();
		this.imgPath = bObject.getImgPath();
		this.mainDivision = bObject.getMainDivision() != null ? new DivisionGui(bObject.getMainDivision()) : null;
	}
	
	public BuildingObject toObject() {
		BuildingObject bobj = new BuildingObject();
		
		bobj.setBuildingid(this.buildingid);
		bobj.setName(this.name);
		bobj.setMainDivision(this.mainDivision != null ? this.mainDivision.toObject() : null);
		
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
	

	public DivisionGui getMainDivision() {
		return mainDivision;
	}

	public void setMainDivision(DivisionGui mainDivision) {
		this.mainDivision = mainDivision;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	
}
