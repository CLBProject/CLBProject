package clb.ui.beans;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import clb.business.AnalyzerDataService;
import clb.business.objects.BuildingObject;
import clb.ui.beans.objects.BuildingAnalysisGui;
import clb.ui.beans.objects.BuildingManagementTreeGui;
import clb.ui.beans.objects.BuildingNewManagementGui;

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

	private List<BuildingManagementTreeGui> buildingsToShow;
	private BuildingNewManagementGui newBuilding;

	@PostConstruct
	public void initBuildingManagement() {

		newBuilding = new BuildingNewManagementGui();
		buildingsToShow = clbHomeLoginBean.userHasBuildings() ? 
						clbHomeLoginBean.getUserBuildings().stream().map(BuildingManagementTreeGui::new).collect(Collectors.toList()) : null;
	}

	public void createBuilding() {
		if (newBuilding != null) {

			BuildingObject newBuildingObj = newBuilding.toObject();
			clbHomeLoginBean.addBuildingToUser(newBuildingObj);
		}
	}

	public void deleteBuilding(BuildingManagementTreeGui buildingToDelete) {
		if (buildingToDelete != null) {
			clbHomeLoginBean.deleteBuildingFromUser(buildingToDelete.toObject());
			buildingsToShow.remove(buildingToDelete);
		}
	}
	
	public void showDivisionOptions(NodeSelectEvent event) {
		TreeNode currentTreeNode = event.getTreeNode();
        currentTreeNode.setSelected(true);

        TreeNode parent = currentTreeNode;
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

	public List<BuildingManagementTreeGui> getBuildingsToShow() {
		return buildingsToShow;
	}

	public void setBuildingsToShow(List<BuildingManagementTreeGui> buildingsToShow) {
		this.buildingsToShow = buildingsToShow;
	}

	public BuildingNewManagementGui getNewBuilding() {
		return newBuilding;
	}

	public void setNewBuilding(BuildingNewManagementGui newBuilding) {
		this.newBuilding = newBuilding;
	}

}