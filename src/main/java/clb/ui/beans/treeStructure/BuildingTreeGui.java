package clb.ui.beans.treeStructure;

import java.util.Set;

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

    private TreeNode rootDivision;
    
    private boolean divisionIsSelected;

	public BuildingTreeGui(BuildingObject bObject) {
		super();
		this.buildingid = bObject.getId();
		this.name = bObject.getName();
		this.imgPath = bObject.getImgPath();
		this.location = bObject.getLocation();
		this.divisionIsSelected = false;
		
		this.rootDivision = new DefaultTreeNode(null,null);
		buildTreeDivisions(this.rootDivision,bObject.getDivisions());
	}

	private void buildTreeDivisions(TreeNode treeDivision, Set<DivisionObject> divisions) {
		
		if(divisions != null && divisions.size() > 0) {
			divisions.stream().forEach(division -> 
						buildTreeDivisions(new DefaultTreeNode(new DivisionTreeGui(division, name),treeDivision), division.getChildrenDivisions()));
		}
		
	}

	public boolean hasDivisions() {
		return this.rootDivision != null && this.rootDivision.getChildren() != null && rootDivision.getChildren().size() > 0;
	}
	
	public BuildingObject toObject() {
		BuildingObject bobj = new BuildingObject();

		bobj.setId(this.buildingid);
		bobj.setName(this.name);
		bobj.setLocation(this.location);
		bobj.setImgPath(this.imgPath);

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

	public TreeNode getRootDivision() {
		return rootDivision;
	}

	public void setRootDivision(TreeNode rootDivision) {
		this.rootDivision = rootDivision;
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

	public boolean isDivisionIsSelected() {
		return divisionIsSelected;
	}

	public void setDivisionIsSelected(boolean divisionIsSelected) {
		this.divisionIsSelected = divisionIsSelected;
	}
	
	
}
