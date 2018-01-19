package clb.database;

import java.util.List;

import clb.database.entities.ClbEntity;

public interface ClbDao {

	public void insert(ClbEntity clbEntity);
	
	public void insert(List<ClbEntity> clbEntity);
}
