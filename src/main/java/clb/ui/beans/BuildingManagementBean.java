package clb.ui.beans;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;

import clb.business.AnalyzerDataService;
import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.ui.beans.newobjects.BuildingNewManagementGui;
import clb.ui.beans.newobjects.DivisionNewManagementGui;
import clb.ui.beans.objects.BuildingAnalysisGui;
import clb.ui.beans.treeStructure.BuildingTreeGui;

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

	private List<BuildingTreeGui> buildingsToShow;
	
	private DivisionObject selectedDivision;
	private String selectedBuildingIdNewDivision;
	
	private BuildingNewManagementGui newBuilding;
	private DivisionNewManagementGui newDivision;

	@PostConstruct
	public void initBuildingManagement() {

		newBuilding = new BuildingNewManagementGui();
		newDivision = new DivisionNewManagementGui();
		
		buildingsToShow = clbHomeLoginBean.userHasBuildings() ? 
						clbHomeLoginBean.getUserBuildings().stream()
							.map(BuildingAnalysisGui::toObject)
							.map(BuildingTreeGui::new)
							.collect(Collectors.toList()) : null;
	}

	public void setNewDivisionBuilding(String buildingId) {
		this.selectedBuildingIdNewDivision = buildingId;
		System.out.println(buildingId);
	}
	
	public void createBuilding() {
		if (newBuilding != null) {

			BuildingObject newBuildingObj = newBuilding.toObject();
			clbHomeLoginBean.addBuildingToUser(newBuildingObj);
		}
	}
	
	public void createDivision() {
		if (newDivision != null) {
			DivisionObject divisionObj = new DivisionObject();
			divisionObj.setName(newDivision.getName());
			
			analyzerDataService.saveDivisionForBuildingOrParent(selectedBuildingIdNewDivision, selectedDivision != null ? selectedDivision.getId() : null, divisionObj);
			
			clbHomeLoginBean.loginUser();
		}
	}

	public void deleteBuilding(BuildingTreeGui buildingToDelete) {
		if (buildingToDelete != null) {
			clbHomeLoginBean.deleteBuildingFromUser(buildingToDelete.toObject());
			buildingsToShow.remove(buildingToDelete);
		}
	}
	
	public void deleteDivision() {
		
//		RequestContext context = RequestContext.getCurrentInstance();
//		
//		if(parentDivisionSelected == null) {
//			context.addCallbackParam(NODE_SELECTED_NULL, true);
//			return;
//		}
//		
//		TreeNode parent = parentDivisionSelected.getParent();
//		
//		if(parent == null) {
//			context.addCallbackParam(CANT_DELETE_DIVISION, true);
//			return;
//		}
//		
//		DivisionNodeTreeGui parentNode = (DivisionNodeTreeGui) parent.getData();
//		DivisionNodeTreeGui divisionToDeleteNode = (DivisionNodeTreeGui) parentDivisionSelected.getData();
//		
//		analyzerDataService.deleteChildDivisionFromParent(parentNode.getDivisionId(),divisionToDeleteNode.getDivisionId());
//		
//		clbHomeLoginBean.loginUser();
	}
	
	public void showDivisionOptions(NodeSelectEvent event) {
		/*this.parentDivisionSelected = event.getTreeNode();
		this.parentDivisionSelected.setSelected(true);
		BuildingTreeGui buildingGui = (BuildingTreeGui)event.getComponent().getAttributes().get("building");
		buildingGui.setMainDivisionIsSelected(true);*/
	}
	
	public void hideDivisionOptions(NodeUnselectEvent event) {
		/*this.parentDivisionSelected = event.getTreeNode();
		this.parentDivisionSelected.setSelected(false);
		BuildingTreeGui buildingGui = (BuildingTreeGui)event.getComponent().getAttributes().get("building");
		buildingGui.setMainDivisionIsSelected(false);*/
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

	public List<BuildingTreeGui> getBuildingsToShow() {
		return buildingsToShow;
	}

	public void setBuildingsToShow(List<BuildingTreeGui> buildingsToShow) {
		this.buildingsToShow = buildingsToShow;
	}

	public BuildingNewManagementGui getNewBuilding() {
		return newBuilding;
	}

	public void setNewBuilding(BuildingNewManagementGui newBuilding) {
		this.newBuilding = newBuilding;
	}

	public DivisionNewManagementGui getNewDivision() {
		return newDivision;
	}

	public void setNewDivision(DivisionNewManagementGui newDivision) {
		this.newDivision = newDivision;
	}

	public DivisionObject getSelectedDivision() {
		return selectedDivision;
	}

	public void setSelectedDivision(DivisionObject selectedDivision) {
		this.selectedDivision = selectedDivision;
	}

	public String getSelectedBuildingIdNewDivision() {
		return selectedBuildingIdNewDivision;
	}

	public void setSelectedBuildingIdNewDivision(String selectedBuildingIdNewDivision) {
		this.selectedBuildingIdNewDivision = selectedBuildingIdNewDivision;
	}
	
	
}