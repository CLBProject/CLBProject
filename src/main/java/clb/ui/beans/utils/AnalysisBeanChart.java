package clb.ui.beans.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import clb.business.objects.AnalyzerRegistryObject;
import clb.global.AnalyzerMeterValues;
import clb.global.DateUtils;
import clb.ui.beans.objects.AnalyzerRegistryGui;
import clb.ui.enums.ScaleGraphic;
import clb.ui.enums.TimeAnalysisType;

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
	private AnalyzerMeterValues[] buildingMeterQuickAnalysis;

	private LineChartSeries currentSerie;
	private LineChartSeries previousSerie;
	private LineChartSeries nextSerie;

	private List<AnalyzerRegistryGui> previousRegistries;
	private List<AnalyzerRegistryGui> currentRegistries;
	private List<AnalyzerRegistryGui> nextRegistries;

	private Boolean nextAndPreviousSelected;

	private AnalysisBeanCache analysisBeanCache;

	public AnalysisBeanChart(AnalysisBeanCache analysisBeanCache){
		
		this.analysisBeanCache = analysisBeanCache;

		buildingMeterQuickAnalysis = AnalyzerMeterValues.values();

		lineModel = new LineChartModel();
		lineModel.setZoom(false);
		lineModel.setLegendPosition("se");
		lineModel.setMouseoverHighlight( true );
		lineModel.setResetAxesOnResize( false );
		lineModel.setAnimate( true );
		lineModel.setShowPointLabels( false );
		lineModel.setShowDatatip( false );

		//Default
		buildingMeterSelected = AnalyzerMeterValues.POWER.name();

		currentSerie = new LineChartSeries(AnalyzerMeterValues.POWER.getLabel());
		previousSerie = new LineChartSeries(AnalyzerMeterValues.POWER.getLabel());
		nextSerie = new LineChartSeries(AnalyzerMeterValues.POWER.getLabel());

		currentSerie.setShowMarker( false );
		previousSerie.setShowMarker(false);
		nextSerie.setShowMarker(false);
		
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

		lineModel.clear();

		AnalyzerMeterValues buildingMeterParamValue = AnalyzerMeterValues.valueOf(buildingMeterSelected);

		currentSerie = new LineChartSeries(buildingMeterParamValue.getLabel());
		currentSerie.setShowMarker( false );
		lineModel.addSeries( currentSerie );
		
		this.nextAndPreviousSelected = false;
		
		setSeriesRegistriesValues(currentScale,currentSerie,this.currentRegistries,TimeAnalysisType.CURRENT);
	}




	public void affectPreviousAndNextSeries(ScaleGraphic scaleGraphic, Date analysisDate, String analyzerId, int week, int month, int year) {
		removeNextAndPreviousSeriesRegistries();

		boolean addNextSerie = true;

		if(nextAndPreviousSelected) {
			List<AnalyzerRegistryObject> previousSeriesRegistries = new ArrayList<AnalyzerRegistryObject>();
			List<AnalyzerRegistryObject> nextSeriesRegistries = new ArrayList<AnalyzerRegistryObject>();

			String prevDateLabel = "";
			String nextDateLabel = "";

			switch(scaleGraphic) {
			case HOUR:

				Date previousHour = DateUtils.getInstance().getHour(DateUtils.getInstance().getHourReseted(analysisDate), false);
				Date nextHour = DateUtils.getInstance().getHour(DateUtils.getInstance().getHourReseted(analysisDate), true);
 
				previousSeriesRegistries = analysisBeanCache.getHourRegistriesFromAnalyzer( analyzerId,previousHour);

				//This Hour doesnt have next
				if(DateUtils.getInstance().isThisHour(analysisDate)) {
					addNextSerie = false;
				}
				else nextSeriesRegistries = analysisBeanCache.getHourRegistriesFromAnalyzer( analyzerId,nextHour);

				prevDateLabel = DateUtils.getInstance().hourFormat(previousHour);
				nextDateLabel = DateUtils.getInstance().hourFormat(nextHour);

				break;
			case DAY:

				Date previousDay = DateUtils.getInstance().getDay(analysisDate, false);
				Date nextDay = DateUtils.getInstance().getDay(analysisDate, true);

				previousSeriesRegistries = analysisBeanCache.getDayRegistriesFromAnalyzer( analyzerId, previousDay);

				//This Hour doesnt have next
				if(DateUtils.getInstance().isToday(analysisDate)) {
					addNextSerie = false;
				}
				else nextSeriesRegistries = analysisBeanCache.getDayRegistriesFromAnalyzer( analyzerId, nextDay);

				prevDateLabel = DateUtils.getInstance().prettyFormat(previousDay);
				nextDateLabel = DateUtils.getInstance().prettyFormat(nextDay);

				break;
			case WEEK:

				int numberOfWeek5days = DateUtils.getInstance().geWeekNumberOfDays(month, year, week);

				previousSeriesRegistries = analysisBeanCache.getWeekRegistriesFromAnalyzerWithShift( 
						analyzerId, week, month, year, numberOfWeek5days);

				//This Hour doesnt have next
				if(DateUtils.getInstance().isThisWeek(week, month, year)) {
					addNextSerie = false;
				}
				else nextSeriesRegistries = analysisBeanCache.getWeekRegistriesFromAnalyzerWithShift(
						analyzerId, week, month, year, -numberOfWeek5days);


				prevDateLabel = DateUtils.getInstance().weekFormat(DateUtils.getInstance().getWeekFirstDayShift(week, month, year,numberOfWeek5days));
				nextDateLabel = DateUtils.getInstance().weekFormat(DateUtils.getInstance().getWeekFirstDayShift(week,month,year,-numberOfWeek5days));

				break;
			case MONTH:

				previousSeriesRegistries = analysisBeanCache.getMonthRegistriesFromAnalyzerWithShift( analyzerId, month, year, -1);

				//This Hour doesnt have next
				if(DateUtils.getInstance().isThisMonth(month, year)) {
					addNextSerie = false;
				}
				else nextSeriesRegistries = analysisBeanCache.getMonthRegistriesFromAnalyzerWithShift( analyzerId, month , year, 1);

				prevDateLabel = DateUtils.getInstance().monthFormat(DateUtils.getInstance().getMonthFirstDayShift(week, month, year,-1));
				nextDateLabel = DateUtils.getInstance().monthFormat(DateUtils.getInstance().getMonthFirstDayShift(week, month, year,1));

				break;
			default: 
				break;
			}
			
			String currentMeterSelected = AnalyzerMeterValues.valueOf(buildingMeterSelected).getLabel();
			
			currentSerie.setLabel(currentMeterSelected);
			
			previousSerie = new LineChartSeries(currentMeterSelected+" - "+prevDateLabel);
			initSerie(scaleGraphic,previousSerie,previousSeriesRegistries,previousRegistries, TimeAnalysisType.PREVIOUS);
			
			if(addNextSerie) {
				nextSerie = new LineChartSeries(currentMeterSelected+" - "+nextDateLabel);
				initSerie(scaleGraphic,nextSerie,nextSeriesRegistries,nextRegistries, TimeAnalysisType.NEXT);
			}
		}

	}
	
	private void initSerie(ScaleGraphic currentScale, LineChartSeries serie, List<AnalyzerRegistryObject> registries, 
			List<AnalyzerRegistryGui> registriesToProduce, TimeAnalysisType timeAnalysisType) {

		serie.setShowMarker(false);
		lineModel.addSeries( serie );
		registriesToProduce = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries, currentScale);
		setSeriesRegistriesValues(currentScale,serie,registriesToProduce,timeAnalysisType);
	}

	private void removeNextAndPreviousSeriesRegistries() {

		lineModel.getSeries().remove(previousSerie);
		previousSerie.getData().clear();
		
		lineModel.getSeries().remove(nextSerie);
		nextSerie.getData().clear();
		
	}



	private void setSeriesRegistriesValues(ScaleGraphic currentScale, LineChartSeries chartSerie,
			List<AnalyzerRegistryGui> registriesSelected, TimeAnalysisType timeAnalysisType){


		AnalyzerMeterValues buildingMeterSel = AnalyzerMeterValues.valueOf(buildingMeterSelected);

		for(AnalyzerRegistryGui registry: registriesSelected) {
			Number asys = getChartNumberFormated(registry.getAsys());
			Number hz = getChartNumberFormated(registry.getHz());
			Number kwsys = getChartNumberFormated(registry.getKwsys());
			Number pfsys = getChartNumberFormated(registry.getPfsys());
			Number kvarsys = getChartNumberFormated(registry.getKvarsys());
			Number kvasys = getChartNumberFormated(registry.getKvasys());
			Number vlnsys = getChartNumberFormated(registry.getVlnsys());
			Number vllsys = getChartNumberFormated(registry.getVllsys());
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
		yAxis.setLabel(buildingMeterSel.getLabel() + " (" + buildingMeterSel.getUnit() + ")");

		updateSeriesMinAndMaxValue(currentScale);
	}

	private Number getChartNumberFormated(Double number) {
		return number > 10 ? number.intValue() : number;
	}

	private String getTimeString(Date currentTime, ScaleGraphic scale, TimeAnalysisType timeAnalysisType) {

		Date selectedDate = null;

		switch(timeAnalysisType) {
		case PREVIOUS:
			switch(scale) {
			case HOUR:
				selectedDate = DateUtils.getInstance().getHour(currentTime, true);
				break;
			case DAY:
				selectedDate = DateUtils.getInstance().getDay(currentTime, true);
				break;
			case WEEK:
				selectedDate = DateUtils.getInstance().getWeekToDate(currentTime, true);
				return DateUtils.getInstance().convertDateToSimpleStringFormat(selectedDate);
			case MONTH:
				selectedDate = DateUtils.getInstance().getMonthToDate(currentTime, true);
				break;
			}
			break;
		case CURRENT:
			selectedDate = currentTime;
			break;
		case NEXT:
			switch(scale) {
			case HOUR:
				selectedDate = DateUtils.getInstance().getHour(currentTime, false);
				break;
			case DAY:
				selectedDate = DateUtils.getInstance().getDay(currentTime, false);
				break;
			case WEEK:
				selectedDate = DateUtils.getInstance().getWeekToDate(currentTime, false);
				return DateUtils.getInstance().convertDateToSimpleStringFormat(selectedDate);
			case MONTH:
				selectedDate = DateUtils.getInstance().getMonthToDate(currentTime, false);
				break;
			}
		}

		return DateUtils.getInstance().convertDateToSimpleStringFormat(selectedDate);
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

		if(maxValue > 10) {
			yAxis.setMax(maxValue+maxValue*0.1);
			yAxis.setMin(minValue-minValue*0.1);
			yAxis.setTickFormat("%d");
		}
		else {
			yAxis.setMax(maxValue+1);
			yAxis.setMin(minValue-1);
			yAxis.setTickFormat("%.2f");
		}

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

	public AnalyzerMeterValues[] getBuildingMeterQuickAnalysis() {
		return buildingMeterQuickAnalysis;
	}

	public void setBuildingMeterQuickAnalysis(AnalyzerMeterValues[] buildingMeterQuickAnalysis) {
		this.buildingMeterQuickAnalysis = buildingMeterQuickAnalysis;
	}


	public Boolean getNextAndPreviousSelected() {
		return nextAndPreviousSelected;
	}


	public void setNextAndPreviousSelected(Boolean nextAndPreviousSelected) {
		this.nextAndPreviousSelected = nextAndPreviousSelected;
	}

}
