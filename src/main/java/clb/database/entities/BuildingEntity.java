package clb.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the BuildingMeters database table.
 * 
 */

@Document(collection="Buildings")
public class BuildingEntity implements  Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String buildingid;

	private String name;
	
	private String buildingusername;
	
	private String imgPath;
	
	@DBRef
	private List<BuildingMeterEntity> buildingMeters;

	@DBRef
	private List<DataLoggerEntity> dataLoggers;

	public BuildingEntity() {
	}

	public String getBuildingid() {
		return this.buildingid;
	}

	public void setBuildingid(String buildingid) {
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath( String imgPath ) {
        this.imgPath = imgPath;
    }
    

    public List<BuildingMeterEntity> getBuildingMeters() {
        return buildingMeters;
    }

    public void setBuildingMeters( List<BuildingMeterEntity> buildingMeters ) {
        this.buildingMeters = buildingMeters;
    }

    public void addBuildingMeter(BuildingMeterEntity buildingMeterEntity) {
        if(buildingMeters == null) {
            buildingMeters = new ArrayList<BuildingMeterEntity>();
        }
        
        buildingMeters.add(buildingMeterEntity);
    }
}