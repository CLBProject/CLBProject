package clb.ui.beans.objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class BuildingManagementGui {

	private String buildingId;
	
    @NotNull(message="Building Name can't be empty")
    @NotEmpty
	private String buildingName;
	
    @NotNull(message="Building Location can't be empty")
    @NotEmpty
	private String location;
	
	private TreeNode division;
	
	private Integer index;

	public BuildingManagementGui(BuildingGui building) {
		this.buildingId = building.getBuildingid();
		this.buildingName = building.getName();
		this.location = building.getLocation();
		this.index = 0;
		this.division = new DefaultTreeNode(building.getMainDivision() != null ? new DivisionNodeGui(building.getMainDivision()): "");
	}
	

	public BuildingGui toBuildingGui() {
		BuildingGui building = new BuildingGui();
		building.setName(this.buildingName);
		building.setBuildingid(this.buildingId);
		building.setLocation(this.location);
		building.setMainDivision((DivisionGui)this.division.getData());
		return building;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildingId == null) ? 0 : buildingId.hashCode());
		result = prime * result + ((buildingName == null) ? 0 : buildingName.hashCode());
		result = prime * result + ((division == null) ? 0 : division.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		BuildingManagementGui other = (BuildingManagementGui) obj;
		if (buildingId == null) {
			if (other.buildingId != null)
				return false;
		} else if (!buildingId.equals(other.buildingId))
			return false;
		if (buildingName == null) {
			if (other.buildingName != null)
				return false;
		} else if (!buildingName.equals(other.buildingName))
			return false;
		if (division == null) {
			if (other.division != null)
				return false;
		} else if (!division.equals(other.division))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
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


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}

	
	
}
