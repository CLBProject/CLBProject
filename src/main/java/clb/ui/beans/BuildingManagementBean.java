package clb.ui.beans;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import clb.business.AnalyzerDataService;
import clb.ui.beans.objects.BuildingGui;
import clb.ui.beans.objects.BuildingManagementGui;

@ViewScoped
@ManagedBean
public class BuildingManagementBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
    @ManagedProperty("#{analyzerDataService}")
    private AnalyzerDataService analyzerDataService;
    
    @ManagedProperty("#{clbHomeLoginBean}")
    private ClbHomeLoginBean clbHomeLoginBean;
    
    private List<BuildingManagementGui> buildingsToShow;
	
	@PostConstruct
	public void initBuildingManagement() {
		
		buildingsToShow = clbHomeLoginBean.getUserBuildings().stream()
				.map(BuildingManagementGui::new)
				.collect(Collectors.toList());
	}

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}

	public void setAnalyzerDataService(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;
	}

	public ClbHomeLoginBean getClbHomeLoginBean() {
		return clbHomeLoginBean;
	}

	public void setClbHomeLoginBean(ClbHomeLoginBean clbHomeLoginBean) {
		this.clbHomeLoginBean = clbHomeLoginBean;
	}

	public List<BuildingManagementGui> getBuildingsToShow() {
		return buildingsToShow;
	}

	public void setBuildingsToShow(List<BuildingManagementGui> buildingsToShow) {
		this.buildingsToShow = buildingsToShow;
	}
	
	
}