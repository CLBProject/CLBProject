package clb.beans.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import clb.beans.enums.ScaleGraphic;
import clb.beans.enums.TimeAnalysisType;
import clb.beans.pojos.AnalyzerRegistryGui;
import clb.beans.pojos.AnalyzerRegistryReductionAlgorithm;
import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.global.BuildingMeterParameterValues;
import clb.global.DateUtils;

public class AnalysisBeanChart {

	private LineChartModel lineModel;

	private static final int STICK_ANGLE_GRAPHIC_LINE = -50;
	private static final int STICK_ANGLE_GRAPHIC_LINE_WEEK = -60;
	private static final String DATE_HOUR_INTERVAL_GRAPHIC = "300";
	private static final String DATE_DAY_INTERVAL_GRAPHIC = "3600";
	private static final String DATE_WEEK_INTERVAL_GRAPHIC = "86400";
	private static final String DATE_MONTH_INTERVAL_GRAPHIC = "86400";
	private static final String DATE_FORMAT_GRAPHIC = "%H:%M:%S";
	private static final String DATE_FORMAT_GRAPHIC_WEEK= "%A %#d, %B";

	private String buildingMeterSelected;
	private BuildingMeterParameterValues[] buildingMeterQuickAnalysis;

	private LineChartSeries currentSerie;
	private LineChartSeries previousSerie;
	private LineChartSeries nextSerie;

	private List<AnalyzerRegistryGui> previousRegistries;
	private List<AnalyzerRegistryGui> currentRegistries;
	private List<AnalyzerRegistryGui> nextRegistries;

	private Boolean nextAndPreviousSelected;

	private AnalyzerDataService analyzerDataService;

	public AnalysisBeanChart(List<BuildingMeterObject> buildingMetersObject, AnalyzerDataService analyzerDataService){

		this.analyzerDataService = analyzerDataService;

		buildingMeterQuickAnalysis = BuildingMeterParameterValues.values();

		lineModel = new LineChartModel();
		lineModel.setZoom(false);
		lineModel.setLegendPosition("se");
		lineModel.setMouseoverHighlight( true );
		lineModel.setResetAxesOnResize( false );
		lineModel.setAnimate( true );
		lineModel.setShowPointLabels( false );
		lineModel.setShowDatatip( false );

		//Default
		buildingMeterSelected = BuildingMeterParameterValues.POWER.name();

		currentSerie = new LineChartSeries(BuildingMeterParameterValues.POWER.getLabel());
		previousSerie = new LineChartSeries(BuildingMeterParameterValues.POWER.getLabel());
		nextSerie = new LineChartSeries(BuildingMeterParameterValues.POWER.getLabel());

		currentSerie.setShowMarker( false );
		lineModel.addSeries( currentSerie );
		lineModel.getAxes().put(AxisType.X,new DateAxis());


		this.currentRegistries = new ArrayList<AnalyzerRegistryGui>();
		this.previousRegistries = new ArrayList<AnalyzerRegistryGui>();
		this.nextRegistries = new ArrayList<AnalyzerRegistryGui>();

		this.nextAndPreviousSelected = false;
	}


	public void fillGraphicForData(List<AnalyzerRegistryObject> registries, ScaleGraphic currentScale){

		this.currentRegistries = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries, currentScale);

		//Clear All Data
		lineModel.getSeries().stream().forEach( serie -> serie.getData().clear() );

		if(registries.size() > 0) {
			setSeriesRegistriesValues(currentScale,currentSerie,currentRegistries,TimeAnalysisType.CURRENT);
		}

	}

	/**
	 * Changes The Serie to add or remove in the Graphic
	 * @param serieToChange
	 */
	public void changeSerie(ScaleGraphic currentScale){

		Map<Object, Number> dataSerie = currentSerie.getData();
		Map<Object, Number> dataPrevSerie = previousSerie != null ? previousSerie.getData() : null;
		Map<Object, Number> dataNextSerie = nextSerie != null ? nextSerie.getData() : null;

		lineModel.clear();

		BuildingMeterParameterValues buildingMeterParamValue = BuildingMeterParameterValues.valueOf(buildingMeterSelected);

		currentSerie = new LineChartSeries(buildingMeterParamValue.getLabel());
		currentSerie.setShowMarker( false );
		currentSerie.setData(dataSerie);
		lineModel.addSeries( currentSerie );

		//If previous and next are selected
		if(nextAndPreviousSelected) {

			previousSerie = new LineChartSeries(buildingMeterParamValue.getLabel());
			nextSerie = new LineChartSeries(buildingMeterParamValue.getLabel());

			previousSerie.setData(dataPrevSerie);
			nextSerie.setData(dataNextSerie);

			lineModel.addSeries( previousSerie );
			lineModel.addSeries( nextSerie );
		}
		setSeriesRegistriesValues(currentScale,currentSerie,this.currentRegistries,TimeAnalysisType.CURRENT);
	}




	public void affectPreviousAndNextSeries(ScaleGraphic scaleGraphic, Date analysisDate, String analyzerId, int week, int month, int year) {
		removeNextAndPreviousSeriesRegistries();

		if(nextAndPreviousSelected) {
			List<AnalyzerRegistryObject> previousSeriesRegistries = new ArrayList<AnalyzerRegistryObject>();
			List<AnalyzerRegistryObject> nextSeriesRegistries = new ArrayList<AnalyzerRegistryObject>();

			String prevDateLabel = "";
			String nextDateLabel = "";

			switch(scaleGraphic) {
			case HOUR:

				Date previousHour = DateUtils.getInstance().getHourReseted(analysisDate, false);
				Date nextHour = DateUtils.getInstance().getHourReseted(analysisDate, true);

				previousSeriesRegistries = analyzerDataService.getHourRegistriesFromAnalyzer( analyzerId,previousHour);
				nextSeriesRegistries = analyzerDataService.getHourRegistriesFromAnalyzer( analyzerId,nextHour);

				prevDateLabel = DateUtils.getInstance().hourFormat(previousHour);
				nextDateLabel = DateUtils.getInstance().hourFormat(nextHour);

				break;
			case DAY:

				Date previousDay = DateUtils.getInstance().getDay(analysisDate, false);
				Date nextDay = DateUtils.getInstance().getDay(analysisDate, true);

				previousSeriesRegistries = analyzerDataService.getDayRegistriesFromAnalyzer( analyzerId, previousDay);
				nextSeriesRegistries = analyzerDataService.getDayRegistriesFromAnalyzer( analyzerId, nextDay);

				prevDateLabel = DateUtils.getInstance().prettyFormat(previousDay);
				nextDateLabel = DateUtils.getInstance().prettyFormat(nextDay);

				break;
			case WEEK:

				int previousWeek = DateUtils.getInstance().getPreviousWeekFromWeek(week,month,year);
				int previousMonth = DateUtils.getInstance().getPreviousMonthFromWeek(week,month,year);
				int previousYear = DateUtils.getInstance().getPreviousYearFromWeek(week,month,year);


				int nextWeek = DateUtils.getInstance().getNextWeekFromWeek(week,month,year);
				int nextMonth = DateUtils.getInstance().getNextMonthFromWeek(week,month,year);
				int nextYear = DateUtils.getInstance().getNextYearFromWeek(week,month,year);

				previousSeriesRegistries = analyzerDataService.getWeekRegistriesFromAnalyzer( analyzerId, previousWeek, previousMonth, previousYear);
				nextSeriesRegistries = analyzerDataService.getWeekRegistriesFromAnalyzer( analyzerId, nextWeek, nextMonth, nextYear);

				prevDateLabel = DateUtils.getInstance().weekFormat(previousWeek, previousMonth, previousYear);
				nextDateLabel = DateUtils.getInstance().weekFormat(nextWeek, nextMonth, nextYear);
				
			    currentSerie.setLabel(BuildingMeterParameterValues.valueOf(buildingMeterSelected).getLabel() + " - " + 
			    													DateUtils.getInstance().weekFormat(nextWeek, nextMonth, nextYear));
				
				break;
			case MONTH:

				int prevMonth = DateUtils.getInstance().getPreviousMonth(month,year);
				int prevYear = DateUtils.getInstance().getPreviousYear(month,year);

				int nMonth = DateUtils.getInstance().getNextMonth(month,year);
				int nYear = DateUtils.getInstance().getNextYear(month,year);

				previousSeriesRegistries = analyzerDataService.getMonthRegistriesFromAnalyzer( analyzerId, prevMonth, prevYear);
				nextSeriesRegistries = analyzerDataService.getMonthRegistriesFromAnalyzer( analyzerId, nMonth , nYear);

				prevDateLabel = DateUtils.getInstance().monthFormat(prevMonth, prevYear);
				nextDateLabel = DateUtils.getInstance().monthFormat(nMonth, nYear);

				break;
			default: 
				break;
			}

			addPreviousAndNextSeries(scaleGraphic,previousSeriesRegistries,nextSeriesRegistries, prevDateLabel, nextDateLabel);
		}

	}

	private void addPreviousAndNextSeries(ScaleGraphic currentScale, List<AnalyzerRegistryObject> previousSeriesRegistries,
			List<AnalyzerRegistryObject> nextSeriesRegistries,String previousDayLabel, String nextDayLabel){

		previousSerie = new LineChartSeries(BuildingMeterParameterValues.POWER.getLabel()+" - "+previousDayLabel);
		nextSerie = new LineChartSeries(BuildingMeterParameterValues.POWER.getLabel() + " - " + nextDayLabel);

		previousSerie.setShowMarker(false);
		nextSerie.setShowMarker(false);

		lineModel.addSeries( previousSerie );
		lineModel.addSeries( nextSerie );

		previousRegistries = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(previousSeriesRegistries, currentScale);
		nextRegistries =  AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(nextSeriesRegistries, currentScale);

		setSeriesRegistriesValues(currentScale,previousSerie,previousRegistries,TimeAnalysisType.PREVIOUS);
		setSeriesRegistriesValues(currentScale,nextSerie,nextRegistries,TimeAnalysisType.NEXT);
	}

	private void removeNextAndPreviousSeriesRegistries() {
		previousSerie.getData().clear();
		nextSerie.getData().clear();

		lineModel.getSeries().remove(previousSerie);
		lineModel.getSeries().remove(nextSerie);
	}



	private void setSeriesRegistriesValues(ScaleGraphic currentScale, LineChartSeries chartSerie,
			List<AnalyzerRegistryGui> registriesSelected, TimeAnalysisType timeAnalysisType){


		BuildingMeterParameterValues buildingMeterSel = BuildingMeterParameterValues.valueOf(buildingMeterSelected);

		for(AnalyzerRegistryGui registry: registriesSelected) {
			Integer asys = registry.getAsys().intValue();
			Integer hz = registry.getHz().intValue();;
			Integer kwsys = registry.getKwsys().intValue();;
			Integer pfsys = registry.getPfsys().intValue();;
			Integer kvarsys = registry.getKvarsys().intValue();;
			Integer kvasys = registry.getKvasys().intValue();;
			Integer vlnsys = registry.getVlnsys().intValue();;
			Integer vllsys = registry.getVllsys().intValue();;
			String currentTime = getTimeString(registry.getCurrentTime(),currentScale,timeAnalysisType);

			switch(buildingMeterSel) {

			case CURRENT:
				chartSerie.set(currentTime,asys);
				break;
			case FREQUENCY:
				chartSerie.set(currentTime,hz);
				break;
			case POWER:
				chartSerie.set(currentTime,kwsys);
				break;
			case POWER_FACTOR:
				chartSerie.set(currentTime,pfsys);
				break;
			case REACTIVE_POWER:
				chartSerie.set(currentTime,kvarsys);
				break;
			case VOLT_AMPERE:
				chartSerie.set(currentTime,kvasys);
				break;
			case VOLTAGE:
				chartSerie.set(currentTime,vlnsys);
				break;
			case VOLTAGE_BETWEEN_PHASES:
				chartSerie.set(currentTime,vllsys);
				break;
			default:
				chartSerie.set(currentTime,kwsys);
				break;
			}
		}

		Axis xAxis = lineModel.getAxis(AxisType.X);

		switch(currentScale) {
		case HOUR:
			//5 minutes interval
			xAxis.setTickAngle(STICK_ANGLE_GRAPHIC_LINE);
			xAxis.setTickInterval( DATE_HOUR_INTERVAL_GRAPHIC );
			xAxis.setTickFormat(DATE_FORMAT_GRAPHIC);

			break;
		case DAY:
			//20 minutes interval
			xAxis.setTickAngle(STICK_ANGLE_GRAPHIC_LINE);
			xAxis.setTickInterval( DATE_DAY_INTERVAL_GRAPHIC );
			xAxis.setTickFormat(DATE_FORMAT_GRAPHIC);
			break;
		case WEEK:
			//1 day interval
			xAxis.setTickAngle(STICK_ANGLE_GRAPHIC_LINE_WEEK);
			xAxis.setTickInterval( DATE_WEEK_INTERVAL_GRAPHIC );
			xAxis.setTickFormat(DATE_FORMAT_GRAPHIC_WEEK);
			break;
		case MONTH:
			//1 day interval
			xAxis.setTickAngle(STICK_ANGLE_GRAPHIC_LINE_WEEK);
			xAxis.setTickInterval( DATE_MONTH_INTERVAL_GRAPHIC );
			xAxis.setTickFormat(DATE_FORMAT_GRAPHIC_WEEK);
			break;
		default:
			break;

		}

		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setTickFormat("%d");
		yAxis.setLabel(buildingMeterSel.getLabel() + " (" + buildingMeterSel.getUnit() + ")");

		updateSeriesMinAndMaxValue(currentScale);
	}


	private String getTimeString(Date currentTime, ScaleGraphic scale, TimeAnalysisType timeAnalysisType) {

		String timeString = "";

		switch(timeAnalysisType) {
		case PREVIOUS:
			switch(scale) {
				case HOUR:
					timeString = DateUtils.getInstance().convertDateToSimpleStringFormat(
							DateUtils.getInstance().getHourReseted(currentTime, true));
				break;
				case DAY:
					timeString = DateUtils.getInstance().convertDateToSimpleStringFormat(
							DateUtils.getInstance().getDay(currentTime, true));
				break;
				case WEEK:

					timeString = DateUtils.getInstance().convertDateToSimpleStringFormat(
							DateUtils.getInstance().getWeekToDate(currentTime, true));
				break;
				case MONTH:
					timeString = DateUtils.getInstance().convertDateToSimpleStringFormat(
							DateUtils.getInstance().getMonthToDate(currentTime, true));
				break;
			}
			break;
		case CURRENT:
			timeString = DateUtils.getInstance().convertDateToSimpleStringFormat(currentTime);
			break;
		case NEXT:
			switch(scale) {
				case HOUR:
					timeString = DateUtils.getInstance().convertDateToSimpleStringFormat(
							DateUtils.getInstance().getHourReseted(currentTime, false));
				break;
				case DAY:
					timeString = DateUtils.getInstance().convertDateToSimpleStringFormat(
							DateUtils.getInstance().getDay(currentTime, false));
				break;
				case WEEK:
					timeString = DateUtils.getInstance().convertDateToSimpleStringFormat(
							DateUtils.getInstance().getWeekToDate(currentTime, false));
				break;
				case MONTH:
					timeString = DateUtils.getInstance().convertDateToSimpleStringFormat(
							DateUtils.getInstance().getMonthToDate(currentTime, false));
				break;
			}
		}

		return timeString;
	}


	private void updateSeriesMinAndMaxValue(ScaleGraphic currentScale){

		int minValue = Integer.MAX_VALUE;
		int maxValue = Integer.MIN_VALUE;

		String minDate = null;
		String maxDate = null;

		for(ChartSeries serie: lineModel.getSeries()) {
			for(Entry<Object,Number> entry: serie.getData().entrySet()) {

				String currentTime = (String)entry.getKey(); 
				Integer serieValue = entry.getValue().intValue();

				if(serieValue < minValue) {
					minValue = serieValue;
				}

				if(serieValue > maxValue) {
					maxValue = serieValue;
				}

				//Get Max Date for Graphic
				if(maxDate == null || currentTime.compareTo( maxDate ) > 0) {
					maxDate = currentTime;
				}

				//Get Min Date for Graphic
				if(minDate == null || currentTime.compareTo( minDate) < 0) {
					minDate = currentTime;
				}
			}
		}

		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setMax(maxValue);
		yAxis.setMin(minValue);

		Axis xAxis = lineModel.getAxis(AxisType.X);
		xAxis.setMax(maxDate);
		xAxis.setMin(minDate);
	}


	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel( LineChartModel lineModel ) {
		this.lineModel = lineModel;
	}

	public String getBuildingMeterSelected() {
		return buildingMeterSelected;
	}

	public void setBuildingMeterSelected(String buildingMeterSelected) {
		this.buildingMeterSelected = buildingMeterSelected;
	}

	public BuildingMeterParameterValues[] getBuildingMeterQuickAnalysis() {
		return buildingMeterQuickAnalysis;
	}

	public void setBuildingMeterQuickAnalysis(BuildingMeterParameterValues[] buildingMeterQuickAnalysis) {
		this.buildingMeterQuickAnalysis = buildingMeterQuickAnalysis;
	}


	public Boolean getNextAndPreviousSelected() {
		return nextAndPreviousSelected;
	}


	public void setNextAndPreviousSelected(Boolean nextAndPreviousSelected) {
		this.nextAndPreviousSelected = nextAndPreviousSelected;
	}


	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}


	public void setAnalyzerDataService(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;
	}


}
