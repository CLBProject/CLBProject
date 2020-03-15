package clb.ui.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.extensions.event.TimeSelectEvent;
import org.primefaces.model.TreeNode;

import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.business.services.AnalyzerDataService;
import clb.global.AnalyzerMeterValues;
import clb.global.DateUtils;
import clb.ui.beans.newobjects.AnalyzerNewManagementGui;
import clb.ui.beans.newobjects.BuildingNewManagementGui;
import clb.ui.beans.newobjects.DivisionNewManagementGui;
import clb.ui.beans.objects.AnalyzerGui;
import clb.ui.beans.objects.BuildingGui;
import clb.ui.beans.treeStructure.BuildingTreeGui;
import clb.ui.beans.treeStructure.DivisionTreeGui;
import clb.ui.beans.utils.AnalysisBeanCache;
import clb.ui.beans.utils.AnalysisBeanChart;
import clb.ui.enums.ScaleGraphic;

@ViewScoped
@ManagedBean
public class AnalysisBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{clbHomeLoginBean}")
	private ClbHomeLoginBean clbHomeLoginBean;

	@ManagedProperty("#{analyzerDataService}")
	private AnalyzerDataService analyzerDataService;
	
	private List<BuildingTreeGui> buildingsToShow;
	private List<AnalyzerGui> analyzersFromDivision;
	
	private BuildingNewManagementGui newBuilding;
	private DivisionNewManagementGui newDivision;
	private AnalyzerNewManagementGui newAnalyzer;

	private TreeNode parentDivisionSelected;
	private BuildingTreeGui selectedBuilding;
	private AnalyzerGui selectedAnalyzer;
	private Date currentDateAnalyzer;
	private Date minCurrentDateAnalyzer;
	private Date maxCurrentDateAnalyzer;

	private List<AnalyzerMeterValues> analyzerMeterValues;
	private AnalyzerMeterValues analyzerMeter;

	private AnalysisBeanChart analysisDayPojo;
	private AnalysisBeanCache analysisBeanCache;
	
	private ScaleGraphic scaleGraphic;
	private ScaleGraphic[] scalesGraphic;
	
	private Date hour;
	private Boolean updateSeries;

	@PostConstruct
	public void init() {
		
		fillBuildingsData();
		
		analysisBeanCache = new AnalysisBeanCache(analyzerDataService);
		
		scalesGraphic = ScaleGraphic.values();
		scaleGraphic = ScaleGraphic.DAY;
		
		hour = new Date();
		updateSeries = false;
		
	}
	
	private void fillBuildingsData() {
		
		this.newBuilding = new BuildingNewManagementGui();
		this.newDivision = new DivisionNewManagementGui();
		this.newAnalyzer = new AnalyzerNewManagementGui();
		
		this.parentDivisionSelected = null;
		this.selectedBuilding = null;
		this.selectedAnalyzer = null;
		
		this.buildingsToShow = clbHomeLoginBean.getAuthenticatedUser().hasBuildings() ? 
								clbHomeLoginBean.getAuthenticatedUser().getBuildings()
																			.stream()
																			.map(BuildingGui::toObject)
																			.map(BuildingTreeGui::new).collect(Collectors.toList()) : new ArrayList<BuildingTreeGui>();
	}

	public void selectDivision(NodeSelectEvent event) {
		this.parentDivisionSelected = event.getTreeNode();
		this.parentDivisionSelected.setSelected(true);

		this.analyzersFromDivision = ((DivisionTreeGui) this.parentDivisionSelected.getData()).getAnalyzers();
		
		this.selectedAnalyzer = null;
	}

	public void unselectDivision(NodeUnselectEvent event) {
		event.getTreeNode().setSelected(false);
		this.parentDivisionSelected = null;
	}
	
	
	public void createBuilding() {
		if (newBuilding != null) {
			BuildingObject newBuildingObj = newBuilding.toObject();
			clbHomeLoginBean.addBuildingToUser(newBuildingObj);
			fillBuildingsData();
		}
		
	}
	
	public void deleteBuilding(BuildingTreeGui buildingToDelete) {
		if (buildingToDelete != null) {
			clbHomeLoginBean.deleteBuildingFromUser(buildingToDelete.toObject());
			buildingsToShow.remove(buildingToDelete);
		}
	}
	
	public void createDivision() {
		if (newDivision != null) {
			DivisionObject divisionObj = new DivisionObject();
			divisionObj.setName(newDivision.getName());

			if (parentDivisionSelected == null) {
				analyzerDataService.saveDivisionForBuilding(this.selectedBuilding.getBuildingid(), divisionObj);
			} else {
				analyzerDataService.saveDivisionForParent(((DivisionTreeGui) parentDivisionSelected.getData()).getDivisionId(), divisionObj);
			}
			
			this.parentDivisionSelected = null;
				
			clbHomeLoginBean.loginUser();
			fillBuildingsData();
		}
	}
	
	public void deleteDivision(String buildId) {

		DivisionTreeGui divisionToDeleteNode = (DivisionTreeGui) parentDivisionSelected.getData();

		TreeNode parent = parentDivisionSelected.getParent();
		DivisionTreeGui parentNode = (DivisionTreeGui) parent.getData();

		if (parentNode != null) {
			analyzerDataService.deleteChildDivisionFromParent(parentNode.getDivisionId(),
					divisionToDeleteNode.getDivisionId());
		} else {
			analyzerDataService.deleteChildDivisionFromBuilding(buildId, divisionToDeleteNode.getDivisionId());
		}

		this.parentDivisionSelected = null;
		
		clbHomeLoginBean.loginUser();
		fillBuildingsData();
	}
	
	public void createAnalyzer(String buildId) {
		String divisionId = ((DivisionTreeGui) this.parentDivisionSelected.getData()).getDivisionId();

		analyzerDataService.saveAnalyzersForDivision(clbHomeLoginBean.getLoginUsername(), buildId, divisionId, newAnalyzer.toObject());
		
		clbHomeLoginBean.loginUser();
		fillBuildingsData();
	}
	
	public String removeAnalyzerSelected(String buildId) {
		if (this.parentDivisionSelected != null && this.selectedAnalyzer != null) {
			String divisionId = ((DivisionTreeGui) this.parentDivisionSelected.getData()).getDivisionId();
			analyzerDataService.removeAnalyzerForDivision(this.clbHomeLoginBean.getLoginUsername(), buildId, divisionId, 
					this.selectedAnalyzer.getAnalyzerId());
			clbHomeLoginBean.loginUser();
			fillBuildingsData();
		}

		return "analysis.xhtml?faces-redirect=true";
	}
	
	public void initAnalyzerGraph(SelectEvent event) {
		
		this.analyzerMeter = this.selectedAnalyzer.getAnalyzerMeters().isEmpty() ? null : this.selectedAnalyzer.getAnalyzerMeters().iterator().next();
		analysisDayPojo = new AnalysisBeanChart( this.analysisBeanCache, this.analyzerMeter);
		
		this.selectedAnalyzer.setRegistriesDates(analyzerDataService.getRegistriesDatesFromAnalyzer(this.selectedAnalyzer.getAnalyzerId()));
		List<Date> selectedAnalyzerDates = this.selectedAnalyzer.getRegistriesDates();
		
		if(selectedAnalyzerDates != null && selectedAnalyzerDates.size() > 0) {
			this.minCurrentDateAnalyzer = selectedAnalyzerDates.get(0);
			this.currentDateAnalyzer =  selectedAnalyzerDates.get(selectedAnalyzerDates.size()-1);
			this.maxCurrentDateAnalyzer = selectedAnalyzerDates.get(selectedAnalyzerDates.size()-1);
		}
		
		this.fillGraphicData();
		
	}
	
	public void changeViewData(final AjaxBehaviorEvent event)  {
		this.fillGraphicData();
	}
	
	public void changeViewData(final SelectEvent event)  {
		this.fillGraphicData();
	}
	
	public void changeViewDataHours(TimeSelectEvent event)  {
		this.currentDateAnalyzer = DateUtils.getInstance().mergeHourOfDate(this.currentDateAnalyzer, this.hour);
		this.fillGraphicData();
	}
	
	private void fillGraphicData() {

		analysisDayPojo.fillGraphicForData( selectedAnalyzer.getAnalyzerId(), this.currentDateAnalyzer, this.scaleGraphic, this.analyzerMeter );
	}
	
	public void updatePreviousAndNextSeries() {
		if(updateSeries) {
			analysisDayPojo.affectPreviousAndNextSeries(scaleGraphic,this.currentDateAnalyzer,selectedAnalyzer.getAnalyzerId(), this.analyzerMeter);
		}
	}
	
	
	public ClbHomeLoginBean getClbHomeLoginBean() {
		return clbHomeLoginBean;
	}

	public void setClbHomeLoginBean(ClbHomeLoginBean clbHomeLoginBean) {
		this.clbHomeLoginBean = clbHomeLoginBean;
	}

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}

	public void setAnalyzerDataService(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;
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

	public BuildingTreeGui getSelectedBuilding() {
		return selectedBuilding;
	}

	public void setSelectedBuilding(BuildingTreeGui selectedBuilding) {
		this.selectedBuilding = selectedBuilding;
	}

	public TreeNode getParentDivisionSelected() {
		return parentDivisionSelected;
	}

	public void setParentDivisionSelected(TreeNode parentDivisionSelected) {
		this.parentDivisionSelected = parentDivisionSelected;
	}

	public List<AnalyzerGui> getAnalyzersFromDivision() {
		return analyzersFromDivision;
	}

	public void setAnalyzersFromDivision(List<AnalyzerGui> analyzersFromDivision) {
		this.analyzersFromDivision = analyzersFromDivision;
	}

	public AnalyzerNewManagementGui getNewAnalyzer() {
		return newAnalyzer;
	}

	public void setNewAnalyzer(AnalyzerNewManagementGui newAnalyzer) {
		this.newAnalyzer = newAnalyzer;
	}

	public List<AnalyzerMeterValues> getAnalyzerMeterValues() {
		return analyzerMeterValues;
	}

	public void setAnalyzerMeterValues(List<AnalyzerMeterValues> analyzerMeterValues) {
		this.analyzerMeterValues = analyzerMeterValues;
	}

	public AnalyzerGui getSelectedAnalyzer() {
		return selectedAnalyzer;
	}

	public void setSelectedAnalyzer(AnalyzerGui selectedAnalyzer) {
		this.selectedAnalyzer = selectedAnalyzer;
	}

	public AnalysisBeanChart getAnalysisDayPojo() {
		return analysisDayPojo;
	}

	public void setAnalysisDayPojo(AnalysisBeanChart analysisDayPojo) {
		this.analysisDayPojo = analysisDayPojo;
	}

	public AnalysisBeanCache getAnalysisBeanCache() {
		return analysisBeanCache;
	}

	public void setAnalysisBeanCache(AnalysisBeanCache analysisBeanCache) {
		this.analysisBeanCache = analysisBeanCache;
	}

	public Date getCurrentDateAnalyzer() {
		return currentDateAnalyzer;
	}

	public void setCurrentDateAnalyzer(Date currentDateAnalyzer) {
		this.currentDateAnalyzer = currentDateAnalyzer;
	}

	public Date getMinCurrentDateAnalyzer() {
		return minCurrentDateAnalyzer;
	}

	public void setMinCurrentDateAnalyzer(Date minCurrentDateAnalyzer) {
		this.minCurrentDateAnalyzer = minCurrentDateAnalyzer;
	}

	public Date getMaxCurrentDateAnalyzer() {
		return maxCurrentDateAnalyzer;
	}

	public void setMaxCurrentDateAnalyzer(Date maxCurrentDateAnalyzer) {
		this.maxCurrentDateAnalyzer = maxCurrentDateAnalyzer;
	}

	public ScaleGraphic getScaleGraphic() {
		return scaleGraphic;
	}

	public void setScaleGraphic(ScaleGraphic scaleGraphic) {
		this.scaleGraphic = scaleGraphic;
	}

	public ScaleGraphic[] getScalesGraphic() {
		return scalesGraphic;
	}

	public void setScalesGraphic(ScaleGraphic[] scalesGraphic) {
		this.scalesGraphic = scalesGraphic;
	}

	public Date getHour() {
		return hour;
	}

	public void setHour(Date hour) {
		this.hour = hour;
	}

	public Boolean getUpdateSeries() {
		return updateSeries;
	}

	public void setUpdateSeries(Boolean updateSeries) {
		this.updateSeries = updateSeries;
	}

	public AnalyzerMeterValues getAnalyzerMeter() {
		return analyzerMeter;
	}

	public void setAnalyzerMeter(AnalyzerMeterValues analyzerMeter) {
		this.analyzerMeter = analyzerMeter;
	}
}
