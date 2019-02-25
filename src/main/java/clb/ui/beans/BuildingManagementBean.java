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
import org.primefaces.model.TreeNode;

import clb.business.AnalyzerDataService;
import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.ui.beans.newobjects.BuildingNewManagementGui;
import clb.ui.beans.newobjects.DivisionNewManagementGui;
import clb.ui.beans.objects.BuildingAnalysisGui;
import clb.ui.beans.treeStructure.BuildingTreeGui;
import clb.ui.beans.treeStructure.DivisionNodeTreeGui;

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
	private TreeNode parentDivisionSelected;
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
			
			if(parentDivisionSelected == null) {
				analyzerDataService.saveDivisionForBuilding(selectedBuildingIdNewDivision, divisionObj);
			}
			else {
				analyzerDataService.saveDivisionForParent(((DivisionNodeTreeGui)parentDivisionSelected.getData()).getDivisionId(), divisionObj);
			}
			
			clbHomeLoginBean.loginUser();
		}
	}

	public void deleteBuilding(BuildingTreeGui buildingToDelete) {
		if (buildingToDelete != null) {
			clbHomeLoginBean.deleteBuildingFromUser(buildingToDelete.toObject());
			buildingsToShow.remove(buildingToDelete);
		}
	}
	
	public void deleteDivision(String buildId) {
		
		DivisionNodeTreeGui divisionToDeleteNode = (DivisionNodeTreeGui) parentDivisionSelected.getData();
		
		TreeNode parent = parentDivisionSelected.getParent();
		DivisionNodeTreeGui parentNode = (DivisionNodeTreeGui) parent.getData();
		
		if(parentNode != null) {
			analyzerDataService.deleteChildDivisionFromParent(parentNode.getDivisionId(),divisionToDeleteNode.getDivisionId());
		}
		else {
			analyzerDataService.deleteChildDivisionFromBuilding(buildId,divisionToDeleteNode.getDivisionId());
		}
		
		clbHomeLoginBean.loginUser();
	}
	
	public void showDivisionOptions(NodeSelectEvent event) {
		this.parentDivisionSelected = event.getTreeNode();
		this.parentDivisionSelected.setSelected(true);
		
		BuildingTreeGui buildingGui = (BuildingTreeGui) event.getComponent().getAttributes().get("building");
		buildingGui.setDivisionIsSelected(true);
	}
	
	public void hideDivisionOptions(NodeUnselectEvent event) {
		event.getTreeNode().setSelected(false);
		this.parentDivisionSelected = null;
		
		BuildingTreeGui buildingGui = (BuildingTreeGui) event.getComponent().getAttributes().get("building");
		buildingGui.setDivisionIsSelected(false);
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

	public TreeNode getParentDivisionSelected() {
		return parentDivisionSelected;
	}

	public void setParentDivisionSelected(TreeNode parentDivisionSelected) {
		this.parentDivisionSelected = parentDivisionSelected;
	}

	public String getSelectedBuildingIdNewDivision() {
		return selectedBuildingIdNewDivision;
	}

	public void setSelectedBuildingIdNewDivision(String selectedBuildingIdNewDivision) {
		this.selectedBuildingIdNewDivision = selectedBuildingIdNewDivision;
	}

	
}