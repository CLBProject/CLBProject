package clb.business.objects;

import clb.database.entities.ClbEntity;

public interface ClbObject {
	
	public void setId(String id);
	
	public String getId();

	public ClbEntity toEntity();
}
