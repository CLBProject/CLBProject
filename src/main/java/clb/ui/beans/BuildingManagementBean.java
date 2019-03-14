package clb.ui.beans;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;

import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.ui.beans.newobjects.BuildingNewManagementGui;
import clb.ui.beans.newobjects.DivisionNewManagementGui;
import clb.ui.beans.objects.AnalyzerGui;
import clb.ui.beans.objects.BuildingAnalysisGui;
import clb.ui.beans.treeStructure.BuildingTreeGui;
import clb.ui.beans.treeStructure.DivisionTreeGui;

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
	
	private TreeNode parentDivisionSelected;
	private String selectedBuildingIdNewDivision;
	
	private List<SelectItem> analyzersDivisionSelection;
	private List<String> analyzersToRemove;
	
	private List<AnalyzerGui> analyzersSelected;
	
	private List<AnalyzerGui> tempAnalyzerSelected;
	
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
		
		analyzersSelected = analyzerDataService.getAllAvailableAnalyzers().stream()
								.map(AnalyzerGui::new)
								.collect(Collectors.toList());
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
				analyzerDataService.saveDivisionForParent(((DivisionTreeGui)parentDivisionSelected.getData()).getDivisionId(), divisionObj);
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
		
		DivisionTreeGui divisionToDeleteNode = (DivisionTreeGui) parentDivisionSelected.getData();
		
		TreeNode parent = parentDivisionSelected.getParent();
		DivisionTreeGui parentNode = (DivisionTreeGui) parent.getData();
		
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
		
		this.analyzersDivisionSelection = ( (DivisionTreeGui) this.parentDivisionSelected.getData() ).getAnalyzers();
		
		BuildingTreeGui buildingGui = (BuildingTreeGui) event.getComponent().getAttributes().get("building");
		buildingGui.setDivisionIsSelected(true);
	}
	
	public void hideDivisionOptions(NodeUnselectEvent event) {
		event.getTreeNode().setSelected(false);
		this.parentDivisionSelected = null;
		
		this.analyzersDivisionSelection = null;
		
		BuildingTreeGui buildingGui = (BuildingTreeGui) event.getComponent().getAttributes().get("building");
		buildingGui.setDivisionIsSelected(false);
	}

	public String selectAnalyzer() {
		//Must Have Division and Analyzers
		if(this.parentDivisionSelected != null && tempAnalyzerSelected != null && tempAnalyzerSelected.size() > 0) {
			String parentId = ((DivisionTreeGui)this.parentDivisionSelected.getData()).getDivisionId();
			analyzerDataService.saveAnalyzersForDivision(parentId,tempAnalyzerSelected.stream().map(AnalyzerGui::toObject).collect(Collectors.toList()));
			clbHomeLoginBean.loginUser();
		}
		
		return "buildingManagement.xhtml?faces-redirect=true";
	}
	
	public String removeAnalyzersSelected() {
		if(this.parentDivisionSelected != null && this.analyzersToRemove != null) {
			String divisionId = ((DivisionTreeGui)this.parentDivisionSelected.getData()).getDivisionId();
			analyzerDataService.removeAnalyzersForDivision(divisionId,analyzersToRemove);
			clbHomeLoginBean.loginUser();
		}
		
		return "buildingManagement.xhtml?faces-redirect=true";
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

	public List<SelectItem> getAnalyzersDivisionSelection() {
		return analyzersDivisionSelection;
	}

	public void setAnalyzersDivisionSelection(List<SelectItem> analyzersDivisionSelection) {
		this.analyzersDivisionSelection = analyzersDivisionSelection;
	}
	
	
	public List<AnalyzerGui> getAnalyzersSelected() {
		return analyzersSelected;
	}

	public void setAnalyzersSelected(List<AnalyzerGui> analyzersSelected) {
		this.analyzersSelected = analyzersSelected;
	}

	public List<AnalyzerGui> getTempAnalyzerSelected() {
		return tempAnalyzerSelected;
	}

	public void setTempAnalyzerSelected(List<AnalyzerGui> tempAnalyzerSelected) {
		this.tempAnalyzerSelected = tempAnalyzerSelected;
	}

	public List<String> getAnalyzersToRemove() {
		return analyzersToRemove;
	}

	public void setAnalyzersToRemove(List<String> analyzersToRemove) {
		this.analyzersToRemove = analyzersToRemove;
	}
	
	
	
}