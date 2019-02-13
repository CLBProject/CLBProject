package clb.ui.beans.treeStructure;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;

public class BuildingTreeGui {

	@NotNull
	private String buildingid;

    @NotNull(message="Name can't be empty")
    @NotEmpty
	private String name;
    
    private String location;
    
    private String imgPath;
    
    private TreeNode mainDivision;
    
    private Boolean mainDivisionIsSelected;

	public BuildingTreeGui(BuildingObject bObject) {
		super();
		this.buildingid = bObject.getBuildingid();
		this.name = bObject.getName();
		this.imgPath = bObject.getImgPath();
		this.location = bObject.getLocation();
		
		this.mainDivisionIsSelected = false;
		
		DivisionObject divisionObj = bObject.getMainDivision();
		this.mainDivision = new DefaultTreeNode(new DivisionNodeTreeGui(divisionObj),null);
		buildTreeDivisions(new DefaultTreeNode(new DivisionNodeTreeGui(divisionObj),this.mainDivision),divisionObj);
	}
	
	private void buildTreeDivisions(TreeNode treeDivision, DivisionObject division) {
		if(division.hasChildren()) {
			for(DivisionObject child: division.getChildrenDivisions()) {
				buildTreeDivisions(new DefaultTreeNode(new DivisionNodeTreeGui(child),treeDivision), child);
			}
		}
		
	}

	public BuildingObject toObject() {
		BuildingObject bobj = new BuildingObject();
		
		bobj.setBuildingid(this.buildingid);
		bobj.setName(this.name);
		bobj.setLocation(this.location);
		bobj.setImgPath(this.imgPath);
		//bobj.setMainDivision(((DivisionNodeGui)mainDivision.getData()).to);
		
		return bobj;
	}
	public Boolean getMainDivisionIsSelected() {
		return mainDivisionIsSelected;
	}

	public void setMainDivisionIsSelected(Boolean mainDivisionIsSelected) {
		this.mainDivisionIsSelected = mainDivisionIsSelected;
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
	public TreeNode getMainDivision() {
		return mainDivision;
	}

	public void setMainDivision(TreeNode mainDivision) {
		this.mainDivision = mainDivision;
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
