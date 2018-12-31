package clb.ui.beans.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import clb.business.objects.BuildingObject;

public class BuildingGui {

	@NotNull
	private String buildingid;

    @NotNull(message="Name can't be empty")
    @NotEmpty
	private String name;
    
    private String imgPath;
    
    private List<AnalyzerGui> analyzers;

	public BuildingGui() {
	}

	public BuildingGui(BuildingObject bObject) {
		super();
		this.buildingid = bObject.getBuildingid();
		this.name = bObject.getName();
		this.imgPath = bObject.getImgPath();
		this.analyzers = bObject.getAnalyzers() != null ?
				bObject.getAnalyzers().stream().map(AnalyzerGui::new).collect(Collectors.toList()) : null;
	}
	
	public BuildingObject toObject() {
		BuildingObject bobj = new BuildingObject();
		
		bobj.setBuildingid(this.buildingid);
		bobj.setName(this.name);
		
		return bobj;
	}
	
    public void addAnalyzer(AnalyzerGui dataLoggerObject) {
        if(analyzers == null) {
            analyzers = new ArrayList<AnalyzerGui>();
        }

        analyzers.add(dataLoggerObject);
    }

	public String getBuildingid() {
		return buildingid;
	}

	public void setBuildingid(String buildingid) {
		this.buildingid = buildingid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AnalyzerGui> getAnalyzers() {
		return analyzers;
	}

	public void setAnalyzers(List<AnalyzerGui> analyzers) {
		this.analyzers = analyzers;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	
}
