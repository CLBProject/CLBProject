package clb.ui.beans.newobjects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;

public class DivisionNewManagementGui {

    @NotNull(message="Name can't be empty")
    @NotEmpty
	private String name;

    private DivisionObject parent;

	public DivisionNewManagementGui() {
	}
	
	public DivisionObject toObject() {
		DivisionObject dObj = new DivisionObject();
		
		dObj.setName(this.name);
		
		return dObj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DivisionObject getParent() {
		return parent;
	}

	public void setParent(DivisionObject parent) {
		this.parent = parent;
	}
	
	
}
