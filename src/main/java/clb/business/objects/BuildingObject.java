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

    private List<AnalyzerObject> analyzers;

    public BuildingObject(){

    }

    public BuildingObject( BuildingEntity building ) {
        this.buildingid = building.getBuildingid();
        this.name = building.getName();
        this.buildingusername = building.getBuildingusername();
        this.imgPath = building.getImgPath();
        this.analyzers = building.getAnalyzers() != null ? 
                building.getAnalyzers().stream().map(AnalyzerObject::new).collect(Collectors.toList()) : null;          
    }

    public BuildingEntity toEntity() {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setBuildingid( this.buildingid );
        buildingEntity.setName( this.name );
        buildingEntity.setImgPath( this.imgPath );
        buildingEntity.setAnalyzers(this.analyzers != null ?
                this.analyzers.stream().map(AnalyzerObject::toEntity).collect(Collectors.toList()) : null);

        return buildingEntity;
    }
    
    public void addAnalyzer(AnalyzerObject dataLoggerObject) {
        if(analyzers == null) {
            analyzers = new ArrayList<AnalyzerObject>();
        }

        analyzers.add(dataLoggerObject);
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

    

    public List<AnalyzerObject> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<AnalyzerObject> analyzers) {
		this.analyzers = analyzers;
	}

	public String getImgPath() {
        return imgPath;
    }

    public void setImgPath( String imgPath ) {
        this.imgPath = imgPath;
    }
}
