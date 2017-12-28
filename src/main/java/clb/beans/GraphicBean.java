package clb.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;

import clb.beans.enums.ScaleGraphic;
import clb.beans.pojos.GraphicBarPojo;
import clb.beans.pojos.GraphicLinearPojo;
import clb.business.AnalyzerDataService;
import clb.business.constants.Month;

@ViewScoped
@ManagedBean
public class GraphicBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{analyzerDataService}")
	private AnalyzerDataService analyzerDataService;

	private GraphicLinearPojo graphicDayHoursPojo;
	private GraphicLinearPojo graphicDayPojo;
	private GraphicLinearPojo graphicMonthPojo;
	private GraphicBarPojo graphicYearPojo;

	private boolean aL1Check;
	private boolean aL2Check;
	private boolean aL3Check;

	private ScaleGraphic scaleSelected;

	private ScaleGraphic[] scaleValues;

	private Date dayDate;

	private List<SelectItem> hours;
	private String hourSelected;

	private Month[] months;
	private Month monthSelected;

	private List<Integer> years;
	private Integer yearSelected;

	private boolean enableDayGraphic;
	private boolean enableDayHoursGraphic;
	private boolean enableYearGraphic;
	private boolean enableMonthGraphic;

	@PostConstruct
	public void init(){
	    
	    analyzerDataService.insertMongoData();
	    
//		dayDate = new Date();
//		scaleSelected = ScaleGraphic.DAY;
//		scaleValues = ScaleGraphic.values();
		hours = getLoadHours();
//		months = Month.values();
//		years = analyzerDataService.getRegistryYears();
//		hourSelected = hours.size() > 0 ? (String) hours.get(0).getValue() : null;
//		yearSelected = years.size() > 0 ? years.get(0) : null;
//		monthSelected = months.length > 0 ? months[0] : null;
//
//		graphicDayPojo = new GraphicLinearPojo();
//		graphicDayHoursPojo = new GraphicLinearPojo();
//		graphicYearPojo = new GraphicBarPojo();
//		graphicMonthPojo = new GraphicLinearPojo();
//
//		enableAllVariables();
//
//		try {
//			changeScale();
//		} catch( IOException e ) {
//			e.printStackTrace();
//		}
	}

	public void changeScale() throws IOException{

		enableAllVariables();

		switch(scaleSelected){
		case DAY:
			graphicDayPojo.fillGraphicForDayData( analyzerDataService.getDataByDay(dayDate));
			enableDayGraphic = graphicDayPojo.hasValues();
			enableDayHoursGraphic = false;
			enableMonthGraphic = false;
			enableYearGraphic = false;
			break;
		case HOUR:
			graphicDayHoursPojo.fillGraphicForDayHoursData( analyzerDataService.getDataByDayAndHours(dayDate, hourSelected));
			
			Calendar dayDateCalendar = Calendar.getInstance();
			dayDateCalendar.setTime(dayDate);

			hourSelected = dayDateCalendar.get(Calendar.HOUR_OF_DAY)+"";
			
			enableDayHoursGraphic = graphicDayHoursPojo.hasValues();
			enableDayGraphic = false;
			enableMonthGraphic = false;
			enableYearGraphic = false;
			break;
		case MONTH:
			graphicMonthPojo.fillGraphicForMonthData( analyzerDataService.getDataByYearAndMonths( yearSelected, monthSelected ) );
			enableMonthGraphic = graphicMonthPojo.hasValues();
			enableYearGraphic = false;
			enableDayHoursGraphic = false;
			enableDayGraphic = false;
			break;
		case YEAR:
			graphicYearPojo.fillGraphicForYearData( analyzerDataService.getDataByYear( yearSelected ) );
			enableYearGraphic = graphicYearPojo.hasValues();
			enableMonthGraphic = false;
			enableDayHoursGraphic = false;
			enableDayGraphic = false;
			break;
		default: 
		}
	}

	private void enableAllVariables(){
		aL1Check = true;
		aL2Check = true;
		aL3Check = true;
	}

	private List<SelectItem> getLoadHours() {
		List<SelectItem> hours = new ArrayList<SelectItem>();

		hours.add(new SelectItem("00", "00:00"));
		hours.add(new SelectItem("01", "01:00"));
		hours.add(new SelectItem("02", "02:00"));
		hours.add(new SelectItem("03", "03:00"));
		hours.add(new SelectItem("04", "04:00"));
		hours.add(new SelectItem("05", "05:00"));
		hours.add(new SelectItem("06", "06:00"));
		hours.add(new SelectItem("07", "07:00"));
		hours.add(new SelectItem("08", "08:00"));
		hours.add(new SelectItem("09", "09:00"));
		hours.add(new SelectItem("10", "10:00"));
		hours.add(new SelectItem("11", "11:00"));
		hours.add(new SelectItem("12", "12:00"));
		hours.add(new SelectItem("13", "13:00"));
		hours.add(new SelectItem("14", "14:00"));
		hours.add(new SelectItem("15", "15:00"));
		hours.add(new SelectItem("16", "16:00"));
		hours.add(new SelectItem("17", "17:00"));
		hours.add(new SelectItem("18", "18:00"));
		hours.add(new SelectItem("19", "19:00"));
		hours.add(new SelectItem("20", "20:00"));
		hours.add(new SelectItem("21", "21:00"));
		hours.add(new SelectItem("22", "22:00"));
		hours.add(new SelectItem("23", "23:00"));

		return hours;
	}

	public void updateChartValues(){

	}

	public void updateChartSeries1(){
		switch(scaleSelected){
		case HOUR:
			aL1Check = graphicDayHoursPojo.updateSeries1(aL1Check);
			break;
		case DAY:
			aL1Check = graphicDayPojo.updateSeries1(aL1Check);
			break;
		case MONTH:
			aL1Check = graphicMonthPojo.updateSeries1(aL1Check);
			break;
		case YEAR:
			aL1Check = graphicYearPojo.updateSeries1(aL1Check);
			break;
		}
	}

	public void updateChartSeries2(){
		switch(scaleSelected){
		case HOUR:
			aL2Check = graphicDayHoursPojo.updateSeries2(aL2Check);
			break;
		case DAY:
			aL2Check = graphicDayPojo.updateSeries2(aL2Check);
			break;
		case MONTH:
			aL2Check = graphicMonthPojo.updateSeries2(aL2Check);
			break;
		case YEAR:
			aL2Check = graphicYearPojo.updateSeries2(aL2Check);
			break;
		}
	}

	public void updateChartSeries3(){
		switch(scaleSelected){
		case HOUR:
			aL3Check = graphicDayHoursPojo.updateSeries3(aL3Check);
			break;
		case DAY:
			aL3Check = graphicDayPojo.updateSeries3(aL3Check);
			break;
		case MONTH:
			aL3Check = graphicMonthPojo.updateSeries3(aL3Check);
			break;
		case YEAR:
			aL3Check = graphicYearPojo.updateSeries3(aL3Check);
			break;
		}
	}

	public void onDaySelect(SelectEvent event) throws IOException {
		if(dayDate != null)
			graphicDayPojo.fillGraphicForDayData(analyzerDataService.getDataByDay(dayDate));
	}

	public void onDayHourSelect(SelectEvent event) throws IOException {
		if(dayDate != null && hourSelected != null)
			graphicDayHoursPojo.fillGraphicForDayHoursData( analyzerDataService.getDataByDayAndHours( dayDate, hourSelected ) );
	}

	public void updateDayHoursGraphic(final AjaxBehaviorEvent event) throws IOException{
		if(dayDate != null && hourSelected != null){
			graphicDayHoursPojo.fillGraphicForDayHoursData( analyzerDataService.getDataByDayAndHours( dayDate, hourSelected ) );
		}
	}

	public void updateYearGraphic(final AjaxBehaviorEvent event){
		if(yearSelected != null)
			graphicYearPojo.fillGraphicForYearData(analyzerDataService.getDataByYear(yearSelected));
	}

	public void updateMonthGraphic(final AjaxBehaviorEvent event){
		if(yearSelected != null && monthSelected != null)
			graphicMonthPojo.fillGraphicForMonthData(analyzerDataService.getDataByYearAndMonths( yearSelected, monthSelected ));
	}

	public void fillDatabaseScript() throws IOException{
		analyzerDataService.fillDatabaseDataWithMoreThenOneYears();
	}

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}


	public void setAnalyzerDataService(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;
	}

	public boolean isaL1Check() {
		return aL1Check;
	}


	public void setaL1Check(boolean aL1Check) {
		this.aL1Check = aL1Check;
	}


	public boolean isaL2Check() {
		return aL2Check;
	}


	public void setaL2Check(boolean aL2Check) {
		this.aL2Check = aL2Check;
	}


	public boolean isaL3Check() {
		return aL3Check;
	}


	public void setaL3Check(boolean aL3Check) {
		this.aL3Check = aL3Check;
	}

	public ScaleGraphic getScaleSelected() {
		return scaleSelected;
	}

	public void setScaleSelected( ScaleGraphic scaleSelected ) {
		this.scaleSelected = scaleSelected;
	}

	public ScaleGraphic[] getScaleValues() {
		return scaleValues;
	}

	public void setScaleValues( ScaleGraphic[] scaleValues ) {
		this.scaleValues = scaleValues;
	}

	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate( Date dayDate ) {
		this.dayDate = dayDate;
	}

	public boolean isEnableDayGraphic() {
		return enableDayGraphic;
	}

	public void setEnableDayGraphic(boolean enableDayGraphic) {
		this.enableDayGraphic = enableDayGraphic;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<SelectItem> getHours() {
		return hours;
	}

	public void setHours(List<SelectItem> hours) {
		this.hours = hours;
	}

	public Month[] getMonths() {
		return months;
	}

	public void setMonths( Month[] months ) {
		this.months = months;
	}

	public String getHourSelected() {
		return hourSelected;
	}

	public void setHourSelected(String hourSelected) {
		this.hourSelected = hourSelected;
	}

	public boolean isEnableDayHoursGraphic() {
		return enableDayHoursGraphic;
	}

	public void setEnableDayHoursGraphic(boolean enableDayHoursGraphic) {
		this.enableDayHoursGraphic = enableDayHoursGraphic;
	}

	public Month getMonthSelected() {
		return monthSelected;
	}

	public void setMonthSelected(Month monthSelected) {
		this.monthSelected = monthSelected;
	}

	public Integer getYearSelected() {
		return yearSelected;
	}

	public void setYearSelected(Integer yearSelected) {
		this.yearSelected = yearSelected;
	}

	public boolean isEnableYearGraphic() {
		return enableYearGraphic;
	}

	public void setEnableYearGraphic(boolean enableYearGraphic) {
		this.enableYearGraphic = enableYearGraphic;
	}

	public boolean isEnableMonthGraphic() {
		return enableMonthGraphic;
	}

	public void setEnableMonthGraphic(boolean enableMonthGraphic) {
		this.enableMonthGraphic = enableMonthGraphic;
	}

	public GraphicLinearPojo getGraphicDayPojo() {
		return graphicDayPojo;
	}

	public void setGraphicDayPojo(GraphicLinearPojo graphicDayPojo) {
		this.graphicDayPojo = graphicDayPojo;
	}

	public GraphicLinearPojo getGraphicMonthPojo() {
		return graphicMonthPojo;
	}

	public void setGraphicMonthPojo(GraphicLinearPojo graphicMonthPojo) {
		this.graphicMonthPojo = graphicMonthPojo;
	}

	public GraphicBarPojo getGraphicYearPojo() {
		return graphicYearPojo;
	}

	public void setGraphicYearPojo(GraphicBarPojo graphicYearPojo) {
		this.graphicYearPojo = graphicYearPojo;
	}

	public GraphicLinearPojo getGraphicDayHoursPojo() {
		return graphicDayHoursPojo;
	}

	public void setGraphicDayHoursPojo( GraphicLinearPojo graphicDayHoursPojo ) {
		this.graphicDayHoursPojo = graphicDayHoursPojo;
	}

}
