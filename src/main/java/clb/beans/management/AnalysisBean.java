package clb.beans.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import clb.beans.enums.AnalysisTypes;
import clb.beans.enums.Hours;
import clb.beans.enums.Months;
import clb.beans.enums.ScaleGraphic;
import clb.beans.enums.Weeks;
import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DataLoggerObject;
import clb.global.DateUtils;

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

	private List<BuildingObject> buildingsToSelect;
	private BuildingObject tempBuildingSelected;
	private BuildingObject buildingSelected;

	private List<AnalyzerObject> analyzersSelected;
	private AnalyzerObject tempAnalyzerSelected;
	private AnalyzerObject analyzerSelected;

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
		if(clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings() != null && 
				clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings().size() > 0 ) {

			buildingsToSelect = clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings();

			boolean firstTime = false;

			for(BuildingObject bObj: clbHomeLoginBean.getUserLoginPojo().getCurrentUser().getBuildings()) {
				if(bObj.getBuildingMeters() != null && bObj.getBuildingMeters().size() > 0 && bObj.getDataLoggers() != null) {
					for(DataLoggerObject dlObj: bObj.getDataLoggers()) {
						if(dlObj.getAnalyzers() != null) {
							for(AnalyzerObject aObj : dlObj.getAnalyzers()) {
								if(!firstTime) {
									buildingSelected = bObj;
									tempBuildingSelected = buildingSelected;
									analyzersSelected = dlObj.getAnalyzers(); 
									analyzerSelected = aObj;
									tempAnalyzerSelected = analyzerSelected;

									analysisDayPojo = new AnalysisBeanChart( buildingSelected.getBuildingMeters(), this.analyzerDataService);

									fillGraphicData(analyzerDataService.getDayRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate) );

									firstTime = true;
								}
							}
						}
					}
				}
			}
		}
	}

	public void selectBuilding() {
		buildingSelected = tempBuildingSelected;
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
		fillGraphicData(analyzerDataService.getHourRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate));
	}

	/** Week View Listeners **/

	public void setWeekValue() {
		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(),Integer.parseInt(year));
		fillGraphicData(  analyzerDataService.getWeekRegistriesFromAnalyzer( analyzerSelected.getId(), 
				week.getCode(), month.getValue(), Integer.parseInt(year)) );
	}


	public void setMonthValueForWeek() {

		week = Weeks.WEEK1;
		
		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(), Integer.parseInt(year));

		weeks = Weeks.getWeeksLimited(DateUtils.getInstance().getNumberOfMonthWeeks(month.getValue(),Integer.parseInt(year)));

		fillGraphicData(analyzerDataService.getWeekRegistriesFromAnalyzer(analyzerSelected.getId(), 
				week.getCode(), month.getValue(), Integer.parseInt(year)));
	}

	public void setYearValueForWeek() {

		week = Weeks.WEEK1;
		month = Months.JANUARY;
		
		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(),Integer.parseInt(year));

		monthsValues = Months.getMonthsLimited(DateUtils.getInstance().getNumberOfMonthsInYear(Integer.parseInt(year)));
		weeks = Weeks.getWeeksLimited(DateUtils.getInstance().getNumberOfMonthWeeks(month.getValue(),Integer.parseInt(year)));

		fillGraphicData(analyzerDataService.getWeekRegistriesFromAnalyzer(analyzerSelected.getId(), week.getCode(), month.getValue(), Integer.parseInt(year)));
	}

	/** Month View Listeners **/

	public void setMonthValueForMonth() {

		week = Weeks.WEEK1;
		
		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(), Integer.parseInt(year));

		fillGraphicData(analyzerDataService.getMonthRegistriesFromAnalyzer( analyzerSelected.getId(), month.getValue(), Integer.parseInt(year)));
	}

	public void setYearValueForMonth() {

		week = Weeks.WEEK1;
		month = Months.JANUARY;
		
		analysisDate = DateUtils.getInstance().getWeekFirstDayReseted(week.getCode(),month.getValue(),Integer.parseInt(year));
		monthsValues = Months.getMonthsLimited(DateUtils.getInstance().getNumberOfMonthsInYear(Integer.parseInt(year)));
		
		fillGraphicData(analyzerDataService.getMonthRegistriesFromAnalyzer( analyzerSelected.getId(), month.getValue(), Integer.parseInt(year)));
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
			registries = analyzerDataService.getHourRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate);
			break;
		case DAY:
			registries = analyzerDataService.getDayRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate);
			break;
		case WEEK:
			registries = analyzerDataService.getWeekRegistriesFromAnalyzer( analyzerSelected.getId(), week.getCode(), month.getValue(), Integer.parseInt(year));
			break;
		case MONTH:
			registries = analyzerDataService.getMonthRegistriesFromAnalyzer( analyzerSelected.getId(), month.getValue(), Integer.parseInt(year));
			break;
		default: 
			registries = analyzerDataService.getDayRegistriesFromAnalyzer( analyzerSelected.getId(), analysisDate);
			break;
		}

		fillGraphicData( registries );
	}


	public void updateMeterSelection() {
		analysisDayPojo.changeSerie(scaleGraphic);
	}

	public void updatePreviousAndNextSeries() {
		analysisDayPojo.affectPreviousAndNextSeries(scaleGraphic,analysisDate,analyzerSelected.getId(),week.getCode(), month.getValue(), Integer.parseInt(year));
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

	public BuildingObject getBuildingSelected() {
		return buildingSelected;
	}

	public void setBuildingSelected( BuildingObject buildingSelected ) {
		this.buildingSelected = buildingSelected;
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

	public AnalyzerObject getAnalyzerSelected() {
		return analyzerSelected;
	}

	public void setAnalyzerSelected( AnalyzerObject analyzerSelected ) {
		this.analyzerSelected = analyzerSelected;
	}

	public BuildingObject getTempBuildingSelected() {
		return tempBuildingSelected;
	}

	public void setTempBuildingSelected( BuildingObject tempBuildingSelected ) {
		this.tempBuildingSelected = tempBuildingSelected;
	}

	public AnalyzerObject getTempAnalyzerSelected() {
		return tempAnalyzerSelected;
	}

	public void setTempAnalyzerSelected( AnalyzerObject tempAnalyzerSelected ) {
		this.tempAnalyzerSelected = tempAnalyzerSelected;
	}

	public List<BuildingObject> getBuildingsToSelect() {
		return buildingsToSelect;
	}

	public void setBuildingsToSelect( List<BuildingObject> buildingsToSelect ) {
		this.buildingsToSelect = buildingsToSelect;
	}

	public List<AnalyzerObject> getAnalyzersSelected() {
		return analyzersSelected;
	}

	public void setAnalyzersSelected( List<AnalyzerObject> analyzersSelected ) {
		this.analyzersSelected = analyzersSelected;
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
	
}
