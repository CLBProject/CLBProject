package clb.ui.beans.objects;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class BuildingManagementGui {

	private String buildingId;
	
	private String buildingName;
	
	private TreeNode division;
	
	private Integer index;

	public BuildingManagementGui(BuildingGui building) {
		this.buildingId = building.getBuildingid();
		this.buildingName = building.getName();
		this.index = 0;
		this.division = new DefaultTreeNode(building.getMainDivision() != null ? building.getMainDivision().getName() : "");
	}
	
	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public TreeNode getDivision() {
		return division;
	}

	public void setDivision(TreeNode division) {
		this.division = division;
	}

	
	
}
