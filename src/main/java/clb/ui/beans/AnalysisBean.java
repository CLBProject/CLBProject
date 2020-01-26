package clb.ui.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;

import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.business.services.AnalyzerDataService;
import clb.global.AnalyzerMeterValues;
import clb.ui.beans.newobjects.AnalyzerNewManagementGui;
import clb.ui.beans.newobjects.BuildingNewManagementGui;
import clb.ui.beans.newobjects.DivisionNewManagementGui;
import clb.ui.beans.objects.AnalyzerGui;
import clb.ui.beans.objects.AnalyzerRegistryGui;
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

	private AnalysisBeanChart analysisDayPojo;
	private AnalysisBeanCache analysisBeanCache;
	
	private ScaleGraphic scaleGraphic;
	private ScaleGraphic[] scalesGraphic;
	
	/*private Date todayDate;
	private Date minDate;
	private Date analysisDate;
	private Date previousAnalisysDate;
	private Date nextAnalisysDate;
	private String analysisDatePrettyFormat;
	private AnalysisBeanChart analysisDayPojo;
	private AnalysisBeanCache analysisBeanCache;

	private List<BuildingAnalysisGui> buildingsToSelect;
	private BuildingAnalysisGui tempBuildingSelected;
	private BuildingAnalysisGui buildingSelected;
	
	private TreeNode mainDivision;
	private TreeNode mainDivisionSelected;

	private List<AnalyzerGui> analyzersSelected;
	private AnalyzerGui tempAnalyzerSelected;
	private AnalyzerGui analyzerSelected;

	private AnalysisTypes analysisType;
	private AnalysisTypes[] analysisTypes;

	private ScaleGraphic scaleGraphic;
	private ScaleGraphic[] scalesGraphic;

	private Hours hour;
	private Hours[] hoursValues;

	private Map<String,List<String>> yearAndMonths;
	
	private Months month;
	private Set<Months> monthsValues;

	private String year;
	private Set<String> years;

	private Weeks week;
	private Weeks[] weeks;*/

	@PostConstruct
	public void init() {
		
		fillBuildingsData();
		
		analysisBeanCache = new AnalysisBeanCache(analyzerDataService);
		analyzerMeterValues = Arrays.asList(AnalyzerMeterValues.values());
		
		scalesGraphic = ScaleGraphic.values();
		scaleGraphic = ScaleGraphic.DAY;
		
		/*analysisBeanCache = new AnalysisBeanCache(analyzerDataService);
		
		todayDate = new Date();
		analysisDate = new Date();
		previousAnalisysDate = DateUtils.getInstance().getDay(analysisDate, false);
		nextAnalisysDate = DateUtils.getInstance().getDay(analysisDate, true);

		analysisDatePrettyFormat = DateUtils.getInstance().prettyFormat(analysisDate);
		minDate = analyzerDataService.getLowestAnalyzerRegistryDate();

		analysisTypes = AnalysisTypes.values();
		scalesGraphic = ScaleGraphic.values();
		scaleGraphic = ScaleGraphic.DAY;
		hoursValues = Hours.getHoursLimited(DateUtils.getInstance().getHourFromDate(todayDate));
		hour = Hours.getHourByValue(DateUtils.getInstance().getHourFromDate(todayDate));

		yearAndMonths = analyzerDataService.getYearsAndMonthsAvailable();
		
		if(yearAndMonths != null && yearAndMonths.size() > 0) {
			years = yearAndMonths.keySet();
			year = years.iterator().next();
			
			List<String> monthsOfYear = yearAndMonths.get(year);
			
			if(monthsOfYear != null && monthsOfYear.size() > 0) {
				monthsValues = monthsOfYear.stream()
						.map(monthMap -> Months.getMonthByValue(Integer.parseInt(monthMap)))
						.collect(Collectors.toSet());
				
				if(monthsValues != null && monthsValues.size() > 0) {
					month = monthsValues.iterator().next();
					
					week = Weeks.getWeekByValue(DateUtils.getInstance().getWeekFromDate(analysisDate));
					weeks = Weeks.getWeeksLimited(DateUtils.getInstance().getNumberOfMonthWeeks(month.getValue(),Integer.parseInt(year)));
					
					//Set Initial Selected Building, DataLogger and Analyzer
					if(clbHomeLoginBean.userHasBuildings() ) {

						buildingsToSelect = initBuildingObjects();

						if(buildingsToSelect != null && buildingsToSelect.size() > 0)
							initChartForBuilding(buildingsToSelect.get(0));
					}
				}
			}
		}*/
		
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
		analysisDayPojo = new AnalysisBeanChart( this.analysisBeanCache);
		
		this.selectedAnalyzer.setRegistriesDates(analyzerDataService.getRegistriesDatesFromAnalyzer(this.selectedAnalyzer.getAnalyzerId()));
		List<Date> selectedAnalyzerDates = this.selectedAnalyzer.getRegistriesDates();
		
		if(selectedAnalyzerDates != null && selectedAnalyzerDates.size() > 0) {
			this.minCurrentDateAnalyzer = selectedAnalyzerDates.get(0);
			this.currentDateAnalyzer =  selectedAnalyzerDates.get(selectedAnalyzerDates.size()-1);
			this.maxCurrentDateAnalyzer = selectedAnalyzerDates.get(selectedAnalyzerDates.size()-1);
		}
		
		fillGraphicData(analysisBeanCache.getDayRegistriesFromAnalyzer( this.selectedAnalyzer.getAnalyzerId(), currentDateAnalyzer));
		
	}
	
	private void fillGraphicData(List<AnalyzerRegistryGui> registries) {

		analysisDayPojo.fillGraphicForData( registries, ScaleGraphic.DAY );
		//updatePreviousAndNextSeries();
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

	
	
	/*
	public Map<String, List<String>> getYearAndMonths() {
		return yearAndMonths;
	}

	public void setYearAndMonths(Map<String, List<String>> yearAndMonths) {
		this.yearAndMonths = yearAndMonths;
	}

	private List<BuildingAnalysisGui> initBuildingObjects() {
		return clbHomeLoginBean.getUserBuildings().stream().filter(building -> {
			
			Set<AnalyzerGui> buildingAnalyzers = building.hasDivisions() ? building.getDivisions().iterator().next().getAnalyzers() : null;
			
			return buildingAnalyzers != null && buildingAnalyzers.stream()
					.filter(buildingAnalyzer -> buildingAnalyzer.getAnalyzerMeters().size() > 0)
					.collect(Collectors.toList()).size() > 0;
		}).collect(Collectors.toList());
	}

	private void initChartForBuilding(BuildingAnalysisGui bGui) {
		buildingSelected = bGui;
		tempBuildingSelected = buildingSelected;

		DivisionGui division = bGui.hasDivisions() ? bGui.getDivisions().iterator().next() : null;
		
		if(division != null && division.hasAnalyzers()) {
			
			DivisionObject divisionObj = division.toObject();
			
			//Root
			mainDivision = new DefaultTreeNode(new DivisionTreeGui(divisionObj),null);
			
			buildTreeSelection(new DefaultTreeNode(new DivisionTreeGui(divisionObj),mainDivision),division);
			
			analyzersSelected = new ArrayList<AnalyzerGui>(division.getAnalyzers());
			analyzerSelected = analyzersSelected.get(0);
			tempAnalyzerSelected = analyzerSelected;
			
			analysisDayPojo = new AnalysisBeanChart( this.analysisBeanCache);

			fillGraphicData(analysisBeanCache.getDayRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), DateUtils.getInstance().getDayReseted(analysisDate)) );
		}
		
		
	}
	
	private void buildTreeSelection(TreeNode head, DivisionGui division) {
		
		if(division.hasSubDivisions()) {
			division.getChildrenDivisions().stream()
				.forEach(childDivision -> 
						buildTreeSelection( new DefaultTreeNode(new DivisionTreeGui(childDivision.toObject()),head),childDivision));
		}
	}
	
	public void selectBuilding() {
		buildingSelected = tempBuildingSelected;
		initChartForBuilding(buildingSelected); 
	}

	public void selectAnalyzer() {
		analyzerSelected = tempAnalyzerSelected;
	}


	public void analysisDayCalendarSelect(SelectEvent event) {
		changeView();
	}


	public void analysisHourCalendarSelect(SelectEvent event) {
		hour = Hours.ZERO;
		changeView();
	}

	public void updateHourValues() {
		analysisDate = DateUtils.getInstance().setHourOfDate(analysisDate,hour.getValue());
		fillGraphicData(analysisBeanCache.getHourRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), analysisDate));
	}


	public void setWeekValue() {
		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(),Integer.parseInt(year));
		fillGraphicData(  analysisBeanCache.getWeekRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), 
				week.getCode(), month.getValue(), Integer.parseInt(year)) );
	}


	public void setMonthValueForWeek() {

		week = Weeks.WEEK1;

		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(), Integer.parseInt(year));

		weeks = Weeks.getWeeksLimited(DateUtils.getInstance().getNumberOfMonthWeeks(month.getValue(),Integer.parseInt(year)));

		fillGraphicData(analysisBeanCache.getWeekRegistriesFromAnalyzer(analyzerSelected.getAnalyzerId(), 
				week.getCode(), month.getValue(), Integer.parseInt(year)));
	}

	public void setYearValueForWeek() {

		week = Weeks.WEEK1;
		month = Months.JANUARY;

		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(),Integer.parseInt(year));

		monthsValues = year != null ? yearAndMonths.get(year).stream()
				.map(month -> Months.getMonthByValue(Integer.parseInt(month)))
				.collect(Collectors.toSet()) : null;
				
		weeks = Weeks.getWeeksLimited(DateUtils.getInstance().getNumberOfMonthWeeks(month.getValue(),Integer.parseInt(year)));

		fillGraphicData(analysisBeanCache.getWeekRegistriesFromAnalyzer(analyzerSelected.getAnalyzerId(), week.getCode(), month.getValue(), Integer.parseInt(year)));
	}


	public void setMonthValueForMonth() {

		week = Weeks.WEEK1;

		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(), Integer.parseInt(year));

		fillGraphicData(analysisBeanCache.getMonthRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), month.getValue(), Integer.parseInt(year)));
	}

	public void setYearValueForMonth() {

		week = Weeks.WEEK1;
		month = Months.JANUARY;

		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(),Integer.parseInt(year));
		monthsValues = yearAndMonths.get(year).stream().map(month -> Months.valueOf(month)).collect(Collectors.toSet());

		fillGraphicData(analysisBeanCache.getMonthRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), month.getValue(), Integer.parseInt(year)));
	}

	private void fillGraphicData(List<AnalyzerRegistryObject> registries) {

		analysisDayPojo.fillGraphicForData( registries, scaleGraphic );
		updatePreviousAndNextSeries();
	}


	public void changeView() {

		List<AnalyzerRegistryObject> registries = new ArrayList<AnalyzerRegistryObject>();

		switch(scaleGraphic) {
		case HOUR:
			updateHoursCombo();
			registries = analysisBeanCache.getHourRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), analysisDate);
			break;
		case DAY:
			registries = analysisBeanCache.getDayRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), analysisDate);
			break;
		case WEEK:
			registries = analysisBeanCache.getWeekRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), week.getCode(), month.getValue(), Integer.parseInt(year));
			break;
		case MONTH:
			registries = analysisBeanCache.getMonthRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), month.getValue(), Integer.parseInt(year));
			break;
		default: 
			registries = analysisBeanCache.getDayRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), analysisDate);
			break;
		}

		fillGraphicData( registries );
	}


	public void updateMeterSelection() {
		analysisDayPojo.changeSerie(scaleGraphic);
	}

	public void updatePreviousAndNextSeries() {
		analysisDayPojo.affectPreviousAndNextSeries(scaleGraphic,analysisDate,analyzerSelected.getAnalyzerId(),week.getCode(), month.getValue(), Integer.parseInt(year));
	}

	private void updateHoursCombo() {

		hour = Hours.ZERO;

		if(DateUtils.getInstance().isToday(analysisDate)) {
			hoursValues = Hours.getHoursLimited(DateUtils.getInstance().getHourFromDate(new Date()));
		}
		else hoursValues = Hours.values();
	}

	public Date getAnalysisDate() {
		return analysisDate;
	}

	public void setAnalysisDate( Date analysisDate ) {
		this.analysisDate = analysisDate;
		this.setAnalysisDatePrettyFormat(DateUtils.getInstance().prettyFormat(analysisDate));
	}

	public AnalysisBeanChart getAnalysisDayPojo() {
		return analysisDayPojo;
	}

	public void setAnalysisDayPojo( AnalysisBeanChart analysisDayPojo ) {
		this.analysisDayPojo = analysisDayPojo;
	}

	public AnalysisTypes getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType( AnalysisTypes analysisType ) {
		this.analysisType = analysisType;
	}

	public AnalysisTypes[] getAnalysisTypes() {
		return analysisTypes;
	}

	public void setAnalysisTypes( AnalysisTypes[] analysisTypes ) {
		this.analysisTypes = analysisTypes;
	}

	public ScaleGraphic[] getScalesGraphic() {
		return scalesGraphic;
	}

	public void setScalesGraphic( ScaleGraphic[] scalesGraphic ) {
		this.scalesGraphic = scalesGraphic;
	}

	public ScaleGraphic getScaleGraphic() {
		return scaleGraphic;
	}

	public void setScaleGraphic( ScaleGraphic scaleGraphic ) {
		this.scaleGraphic = scaleGraphic;
	}

	public ClbHomeLoginBean getClbHomeLoginBean() {
		return clbHomeLoginBean;
	}

	public void setClbHomeLoginBean( ClbHomeLoginBean clbHomeLoginBean ) {
		this.clbHomeLoginBean = clbHomeLoginBean;
	}

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}

	public void setAnalyzerDataService( AnalyzerDataService analyzerDataService ) {
		this.analyzerDataService = analyzerDataService;
	}


	public List<BuildingAnalysisGui> getBuildingsToSelect() {
		return buildingsToSelect;
	}

	public void setBuildingsToSelect( List<BuildingAnalysisGui> buildingsToSelect ) {
		this.buildingsToSelect = buildingsToSelect;
	}


	public Hours getHour() {
		return hour;
	}

	public void setHour(Hours hour) {
		this.hour = hour;
	}

	public Hours[] getHoursValues() {
		return hoursValues;
	}

	public void setHoursValues(Hours[] hoursValues) {
		this.hoursValues = hoursValues;
	}

	public Months getMonth() {
		return month;
	}

	public void setMonth(Months month) {
		this.month = month;
	}

	public Set<Months> getMonthsValues() {
		return monthsValues;
	}

	public void setMonthsValues(Set<Months> monthsValues) {
		this.monthsValues = monthsValues;
	}

	public Date getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(Date todayDate) {
		this.todayDate = todayDate;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public String getAnalysisDatePrettyFormat() {
		return analysisDatePrettyFormat;
	}

	public void setAnalysisDatePrettyFormat(String analysisDatePrettyFormat) {
		this.analysisDatePrettyFormat = analysisDatePrettyFormat;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Set<String> getYears() {
		return years;
	}

	public void setYears(Set<String> years) {
		this.years = years;
	}

	public Weeks getWeek() {
		return week;
	}

	public void setWeek(Weeks week) {
		this.week = week;
	}

	public Weeks[] getWeeks() {
		return weeks;
	}

	public void setWeeks(Weeks[] weeks) {
		this.weeks = weeks;
	}

	public Date getPreviousAnalisysDate() {
		return previousAnalisysDate;
	}

	public void setPreviousAnalisysDate(Date previousAnalisysDate) {
		this.previousAnalisysDate = previousAnalisysDate;
	}

	public Date getNextAnalisysDate() {
		return nextAnalisysDate;
	}

	public void setNextAnalisysDate(Date nextAnalisysDate) {
		this.nextAnalisysDate = nextAnalisysDate;
	}

	public AnalysisBeanCache getAnalysisBeanCache() {
		return analysisBeanCache;
	}

	public void setAnalysisBeanCache(AnalysisBeanCache analysisBeanCache) {
		this.analysisBeanCache = analysisBeanCache;
	}

	public BuildingAnalysisGui getTempBuildingSelected() {
		return tempBuildingSelected;
	}

	public void setTempBuildingSelected(BuildingAnalysisGui tempBuildingSelected) {
		this.tempBuildingSelected = tempBuildingSelected;
	}

	public BuildingAnalysisGui getBuildingSelected() {
		return buildingSelected;
	}

	public void setBuildingSelected(BuildingAnalysisGui buildingSelected) {
		this.buildingSelected = buildingSelected;
	}

	public List<AnalyzerGui> getAnalyzersSelected() {
		return analyzersSelected;
	}

	public void setAnalyzersSelected(List<AnalyzerGui> analyzersSelected) {
		this.analyzersSelected = analyzersSelected;
	}

	public AnalyzerGui getTempAnalyzerSelected() {
		return tempAnalyzerSelected;
	}

	public void setTempAnalyzerSelected(AnalyzerGui tempAnalyzerSelected) {
		this.tempAnalyzerSelected = tempAnalyzerSelected;
	}

	public AnalyzerGui getAnalyzerSelected() {
		return analyzerSelected;
	}

	public void setAnalyzerSelected(AnalyzerGui analyzerSelected) {
		this.analyzerSelected = analyzerSelected;
	}

	public TreeNode getMainDivision() {
		return mainDivision;
	}

	public void setMainDivision(TreeNode mainDivision) {
		this.mainDivision = mainDivision;
	}

	public TreeNode getMainDivisionSelected() {
		return mainDivisionSelected;
	}

	public void setMainDivisionSelected(TreeNode mainDivisionSelected) {
		this.mainDivisionSelected = mainDivisionSelected;
	}*/

	
}
