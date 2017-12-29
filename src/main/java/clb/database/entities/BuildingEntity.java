package clb.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the BUILDING database table.
 * 
 */

@Document(collection="Buildings")
public class BuildingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long buildingid;

	private String name;
	
	private String buildingusername;

	@DBRef
	private List<DataLoggerEntity> dataLoggers;

	public BuildingEntity() {
	}

	public long getBuildingid() {
		return this.buildingid;
	}

	public void setBuildingid(long buildingid) {
		this.buildingid = buildingid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DataLoggerEntity> getDataLoggers() {
        return dataLoggers;
    }

    public void setDataLoggers( List<DataLoggerEntity> dataLoggers ) {
        this.dataLoggers = dataLoggers;
    }

    public String getBuildingusername() {
		return this.buildingusername;
	}

	public void setBuildingusername(String buildingusername) {
		this.buildingusername = buildingusername;
	}

	public void addDataLogger(DataLoggerEntity dataLogger) {
		if(dataLoggers == null) {
			dataLoggers = new ArrayList<DataLoggerEntity>();
		}
		
		dataLoggers.add(dataLogger);
	}
}