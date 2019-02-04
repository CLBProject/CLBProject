package clb.ui.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import clb.business.AnalyzerDataService;
import clb.business.objects.BuildingObject;
import clb.ui.beans.objects.BuildingAnalysisGui;
import clb.ui.beans.objects.BuildingManagementGui;

@ViewScoped
@ManagedBean
public class BuildingManagementBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{analyzerDataService}")
	private AnalyzerDataService analyzerDataService;

	@ManagedProperty("#{clbHomeLoginBean}")
	private ClbHomeLoginBean clbHomeLoginBean;

	private List<BuildingAnalysisGui> buildingsToShow;
	private BuildingManagementGui newBuilding;

	@PostConstruct
	public void initBuildingManagement() {

		newBuilding = new BuildingManagementGui();
		buildingsToShow = clbHomeLoginBean.getUserBuildings();
	}

	public void createBuilding() {
		if (newBuilding != null) {

			BuildingObject newBuildingObj = newBuilding.toObject();
			clbHomeLoginBean.addBuildingToUser(newBuildingObj);
		}
	}

	public void deleteBuilding(BuildingAnalysisGui buildingToDelete) {
		if (buildingToDelete != null) {
			clbHomeLoginBean.deleteBuildingFromUser(buildingToDelete);
			buildingsToShow.remove(buildingToDelete);
		}
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

	public List<BuildingAnalysisGui> getBuildingsToShow() {
		return buildingsToShow;
	}

	public void setBuildingsToShow(List<BuildingAnalysisGui> buildingsToShow) {
		this.buildingsToShow = buildingsToShow;
	}

	public BuildingManagementGui getNewBuilding() {
		return newBuilding;
	}

	public void setNewBuilding(BuildingManagementGui newBuilding) {
		this.newBuilding = newBuilding;
	}

}