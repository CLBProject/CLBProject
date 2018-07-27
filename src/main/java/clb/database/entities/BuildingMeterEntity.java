package clb.database.entities;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the BUILDING database table.
 * 
 */

@Document(collection="BuildingsMeters")
public class BuildingMeterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String buildingMeterId;

	private String name;
	
	private String unit;
	
	private String labelKey;

	@DBRef
	private List<BuildingMeterEntity> buildingMeterParameters;

	public BuildingMeterEntity() {
	}


    public String getBuildingMeterId() {
        return buildingMeterId;
    }


    public void setBuildingMeterId( String buildingMeterId ) {
        this.buildingMeterId = buildingMeterId;
    }


    public String getName() {
        return name;
    }


    public void setName( String name ) {
        this.name = name;
    }


    public List<BuildingMeterEntity> getBuildingMeterParameters() {
        return buildingMeterParameters;
    }


    public void setBuildingMeterParameters( List<BuildingMeterEntity> buildingMeterParameters ) {
        this.buildingMeterParameters = buildingMeterParameters;
    }


    public String getLabelKey() {
        return labelKey;
    }


    public void setLabelKey( String labelKey ) {
        this.labelKey = labelKey;
    }


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}