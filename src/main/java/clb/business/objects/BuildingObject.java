package clb.business.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clb.database.entities.BuildingEntity;

public class BuildingObject
{
    private String buildingid;

    private String name;
    
    private String buildingusername;
    
    private String imgPath;
    
    private List<DataLoggerObject> dataLoggers;

    public BuildingObject(){
        
    }
    
    public BuildingObject( BuildingEntity building ) {
        this.buildingid = building.getBuildingid();
        this.name = building.getName();
        this.buildingusername = building.getBuildingusername();
        this.imgPath = building.getImgPath();
        this.dataLoggers = building.getDataLoggers() != null ? 
        		building.getDataLoggers().stream().map(DataLoggerObject::new).collect(Collectors.toList()) : null;
    }

    public BuildingEntity toEntity() {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setBuildingid( this.buildingid );
        buildingEntity.setName( this.name );
        buildingEntity.setImgPath( this.imgPath );
        buildingEntity.setDataLoggers(this.dataLoggers != null ?
        		this.dataLoggers.stream().map(DataLoggerObject::toEntity).collect(Collectors.toList()) : null);

        return buildingEntity;
    }
    
    public void addDataLogger(DataLoggerObject dataLoggerObject) {
    	if(dataLoggers == null) {
    		dataLoggers = new ArrayList<DataLoggerObject>();
    	}
    	
    	dataLoggers.add(dataLoggerObject);
    }

    public String getBuildingid() {
        return buildingid;
    }

    public void setBuildingid( String buildingid ) {
        this.buildingid = buildingid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

	public String getBuildingusername() {
		return buildingusername;
	}

	public void setBuildingusername(String buildingusername) {
		this.buildingusername = buildingusername;
	}

    public List<DataLoggerObject> getDataLoggers() {
        return dataLoggers;
    }

    public void setDataLoggers( List<DataLoggerObject> dataLoggers ) {
        this.dataLoggers = dataLoggers;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath( String imgPath ) {
        this.imgPath = imgPath;
    }
    
    
}
