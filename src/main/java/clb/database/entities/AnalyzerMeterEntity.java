package clb.database.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the BUILDING database table.
 * 
 */

@Document(collection="BuildingsMeters")
public class AnalyzerMeterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String buildingMeterId;

	private String name;
	
	private String unit;
	
	private String labelKey;

	public AnalyzerMeterEntity() {
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