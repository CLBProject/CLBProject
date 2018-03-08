package clb.database.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the BuildingMetersParameter database table.
 * 
 */

@Document(collection="BuildingsMetersParameters")
public class BuildingMeterParameterEntity implements ClbEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String buildingMeterParameterId;

	private String name;


	public BuildingMeterParameterEntity() {
	}

    public String getBuildingMeterParameterId() {
        return buildingMeterParameterId;
    }

    public void setBuildingMeterParameterId( String buildingMeterParameterId ) {
        this.buildingMeterParameterId = buildingMeterParameterId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
	
	
}