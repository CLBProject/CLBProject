package clb.ui.beans.treeStructure;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

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

	private List<SelectItem> divisions;

	public BuildingTreeGui(BuildingObject bObject) {
		super();
		this.buildingid = bObject.getId();
		this.name = bObject.getName();
		this.imgPath = bObject.getImgPath();
		this.location = bObject.getLocation();
		this.divisions = bObject.getDivisions() != null ? buildTreeDivisions(bObject.getDivisions()) : null;
	}

	private List<SelectItem> buildTreeDivisions(List<DivisionObject> divisions) {

		return divisions.stream().map(division -> {

			if(division.hasChildren()) {
				SelectItemGroup group = new SelectItemGroup(division.getName());
				List<SelectItem> itemsProcessed = buildTreeDivisions(division.getChildrenDivisions());
				group.setSelectItems(itemsProcessed.toArray(new SelectItem[itemsProcessed.size()]));
				return group;
			}

			else return new SelectItem(division, division.getName());
		}).collect(Collectors.toList());
	}

	public boolean hasDivisions() {
		return this.divisions != null && this.divisions.size() > 0;
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

	public List<SelectItem> getDivisions() {
		return divisions;
	}

	public void setDivisions(List<SelectItem> divisions) {
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
