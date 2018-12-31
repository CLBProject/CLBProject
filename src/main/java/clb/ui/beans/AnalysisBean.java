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

import org.primefaces.event.SelectEvent;

import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerRegistryObject;
import clb.global.DateUtils;
import clb.ui.beans.objects.AnalyzerGui;
import clb.ui.beans.objects.BuildingGui;
import clb.ui.beans.utils.AnalysisBeanCache;
import clb.ui.beans.utils.AnalysisBeanChart;
import clb.ui.enums.AnalysisTypes;
import clb.ui.enums.Hours;
import clb.ui.enums.Months;
import clb.ui.enums.ScaleGraphic;
import clb.ui.enums.Weeks;

@ViewScoped
@ManagedBean
public class AnalysisBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{clbHomeLoginBean}")
	private ClbHomeLoginBean clbHomeLoginBean;

	@ManagedProperty("#{analyzerDataService}")
	private AnalyzerDataService analyzerDataService;

	private Date todayDate;
	private Date minDate;
	private Date analysisDate;
	private Date previousAnalisysDate;
	private Date nextAnalisysDate;
	private String analysisDatePrettyFormat;
	private AnalysisBeanChart analysisDayPojo;
	private AnalysisBeanCache analysisBeanCache;

	private List<BuildingGui> buildingsToSelect;
	private BuildingGui tempBuildingSelected;
	private BuildingGui buildingSelected;

	private List<AnalyzerGui> analyzersSelected;
	private AnalyzerGui tempAnalyzerSelected;
	private AnalyzerGui analyzerSelected;

	private AnalysisTypes analysisType;
	private AnalysisTypes[] analysisTypes;

	private ScaleGraphic scaleGraphic;
	private ScaleGraphic[] scalesGraphic;

	private Hours hour;
	private Hours[] hoursValues;

	private Months month;
	private Months[] monthsValues;

	private String year;
	private String[] years;

	private Weeks week;
	private Weeks[] weeks;

	@PostConstruct
	public void init() {

		analysisBeanCache = new AnalysisBeanCache(analyzerDataService);

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

		monthsValues = Months.getMonthsLimited(DateUtils.getInstance().getMonthFromDate(todayDate));
		month = Months.getMonthByValue(DateUtils.getInstance().getMonthFromDate(todayDate));

		year = "" + DateUtils.getInstance().getYearFromDate(todayDate);
		years = analyzerDataService.getYearsAvailable();

		week = Weeks.getWeekByValue(DateUtils.getInstance().getWeekFromDate(analysisDate));
		weeks = Weeks.getWeeksLimited(DateUtils.getInstance().getNumberOfMonthWeeks(month.getValue(),Integer.parseInt(year)));

		//Set Initial Selected Building, DataLogger and Analyzer
		if(clbHomeLoginBean.userHasBuildings() ) {

			buildingsToSelect = initBuildingObjects();

			if(buildingsToSelect != null && buildingsToSelect.size() > 0)
				initChartForBuilding(buildingsToSelect.get(0));
		}
	}

	private List<BuildingGui> initBuildingObjects() {
		return clbHomeLoginBean.getUserBuildings().stream().filter(building -> {
			
			List<AnalyzerGui> buildingAnalyzers = building.getAnalyzers() ;
			
			return buildingAnalyzers != null && buildingAnalyzers.stream()
					.filter(buildingAnalyzer -> buildingAnalyzer.getAnalyzerMeters().size() > 0)
					.collect(Collectors.toList()).size() > 0;
		}).collect(Collectors.toList());
	}

	private void initChartForBuilding(BuildingGui bObj) {
		buildingSelected = bObj;
		tempBuildingSelected = buildingSelected;

		List<AnalyzerGui> analyzers = bObj.getAnalyzers();
		
		if(analyzers != null && analyzers.size() > 0) {
			analyzersSelected = analyzers;
			analyzerSelected = analyzers.get(0);
			tempAnalyzerSelected = analyzerSelected;
			
			analysisDayPojo = new AnalysisBeanChart( this.analysisBeanCache);

			fillGraphicData(analysisBeanCache.getDayRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), DateUtils.getInstance().getDayReseted(analysisDate)) );
		}
		
		
	}

	public void selectBuilding() {
		buildingSelected = tempBuildingSelected;
		initChartForBuilding(buildingSelected); 
	}

	public void selectAnalyzer() {
		analyzerSelected = tempAnalyzerSelected;
	}

	/** Day View Listeners **/

	public void analysisDayCalendarSelect(SelectEvent event) {
		changeView();
	}

	/** Hour View Listeners **/

	public void analysisHourCalendarSelect(SelectEvent event) {
		hour = Hours.ZERO;
		changeView();
	}

	public void updateHourValues() {
		analysisDate = DateUtils.getInstance().setHourOfDate(analysisDate,hour.getValue());
		fillGraphicData(analysisBeanCache.getHourRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), analysisDate));
	}

	/** Week View Listeners **/

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

		monthsValues = Months.getMonthsLimited(DateUtils.getInstance().getNumberOfMonthsInYear(Integer.parseInt(year)));
		weeks = Weeks.getWeeksLimited(DateUtils.getInstance().getNumberOfMonthWeeks(month.getValue(),Integer.parseInt(year)));

		fillGraphicData(analysisBeanCache.getWeekRegistriesFromAnalyzer(analyzerSelected.getAnalyzerId(), week.getCode(), month.getValue(), Integer.parseInt(year)));
	}

	/** Month View Listeners **/

	public void setMonthValueForMonth() {

		week = Weeks.WEEK1;

		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(), Integer.parseInt(year));

		fillGraphicData(analysisBeanCache.getMonthRegistriesFromAnalyzer( analyzerSelected.getAnalyzerId(), month.getValue(), Integer.parseInt(year)));
	}

	public void setYearValueForMonth() {

		week = Weeks.WEEK1;
		month = Months.JANUARY;

		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(),Integer.parseInt(year));
		monthsValues = Months.getMonthsLimited(DateUtils.getInstance().getNumberOfMonthsInYear(Integer.parseInt(year)));

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


	public List<BuildingGui> getBuildingsToSelect() {
		return buildingsToSelect;
	}

	public void setBuildingsToSelect( List<BuildingGui> buildingsToSelect ) {
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

	public Months[] getMonthsValues() {
		return monthsValues;
	}

	public void setMonthsValues(Months[] monthsValues) {
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

	public String[] getYears() {
		return years;
	}

	public void setYears(String[] years) {
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

	public BuildingGui getTempBuildingSelected() {
		return tempBuildingSelected;
	}

	public void setTempBuildingSelected(BuildingGui tempBuildingSelected) {
		this.tempBuildingSelected = tempBuildingSelected;
	}

	public BuildingGui getBuildingSelected() {
		return buildingSelected;
	}

	public void setBuildingSelected(BuildingGui buildingSelected) {
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

	
}
