package clb.beans.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import clb.beans.enums.ScaleGraphic;
import clb.beans.pojos.AnalyzerRegistryGui;
import clb.beans.pojos.AnalyzerRegistryReductionAlgorithm;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.global.BuildingMeterParameterValues;

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
	private List<BuildingMeterParameterValues> buildingMeterQuickAnalysis;

	private LineChartSeries currentSerie;
	private LineChartSeries previousSerie;
	private LineChartSeries nextSerie;
	
	private List<AnalyzerRegistryGui> previousRegistries;
	private List<AnalyzerRegistryGui> currentRegistries;
	private List<AnalyzerRegistryGui> nextRegistries;
	
	private Boolean nextAndPreviousSelected;

	public AnalysisBeanChart(List<BuildingMeterObject> buildingMetersObject){

		buildingMeterQuickAnalysis = new ArrayList<BuildingMeterParameterValues>();
		buildingMeterQuickAnalysis.add(BuildingMeterParameterValues.POWER);
		buildingMeterQuickAnalysis.add(BuildingMeterParameterValues.POWER_FACTOR);
		buildingMeterQuickAnalysis.add(BuildingMeterParameterValues.REACTIVE_POWER);
		buildingMeterQuickAnalysis.add(BuildingMeterParameterValues.CURRENT);
		buildingMeterQuickAnalysis.add(BuildingMeterParameterValues.FREQUENCY);
		buildingMeterQuickAnalysis.add(BuildingMeterParameterValues.VOLT_AMPERE);
		buildingMeterQuickAnalysis.add(BuildingMeterParameterValues.VOLTAGE);
		buildingMeterQuickAnalysis.add(BuildingMeterParameterValues.VOLTAGE_BETWEEN_PHASES);


		lineModel = new LineChartModel();
		lineModel.setZoom(true);
		lineModel.setLegendPosition("se");
		lineModel.setMouseoverHighlight( true );
		lineModel.setResetAxesOnResize( false );
		lineModel.setAnimate( true );
		lineModel.setShowPointLabels( false );
		lineModel.setShowDatatip( false );

		//Default
		currentSerie = new LineChartSeries(BuildingMeterParameterValues.POWER.getLabel());
		previousSerie = new LineChartSeries(BuildingMeterParameterValues.POWER.getLabel());
		nextSerie = new LineChartSeries(BuildingMeterParameterValues.POWER.getLabel());
		
		currentSerie.setShowMarker( false );
		lineModel.addSeries( currentSerie );

		buildingMeterSelected = BuildingMeterParameterValues.POWER.name();
		
		this.currentRegistries = new ArrayList<AnalyzerRegistryGui>();
		this.previousRegistries = new ArrayList<AnalyzerRegistryGui>();
		this.nextRegistries = new ArrayList<AnalyzerRegistryGui>();
	}


	public void fillGraphicForData(List<AnalyzerRegistryObject> registries, ScaleGraphic currentScale){

		this.currentRegistries = AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(registries, currentScale);

		//Clear All Data
		lineModel.getSeries().stream().forEach( serie -> serie.getData().clear() );

		if(registries.size() > 0) {
			updateSeriesRegistriesValues(currentScale,currentSerie,currentRegistries);
		}
	}

	public void addNextAndPreviousSeriesRegistries(ScaleGraphic currentScale, List<AnalyzerRegistryObject> previousSeriesRegistries,
			List<AnalyzerRegistryObject> nextSeriesRegistries) {
		
		previousRegistries =
				AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(previousSeriesRegistries, currentScale);
		
		nextRegistries =  
				AnalyzerRegistryReductionAlgorithm.getInstance().reduceRegistries(nextSeriesRegistries, currentScale);
		
		
		
		updateSeriesRegistriesValues(currentScale,previousSerie,previousRegistries);
		updateSeriesRegistriesValues(currentScale,nextSerie,nextRegistries);
	}
	
	/**
	 * Changes The Serie to add or remove in the Graphic
	 * @param serieToChange
	 */
	public void changeSerie(ScaleGraphic currentScale) {

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

		updateSeriesRegistriesValues(currentScale,currentSerie,this.currentRegistries);
	}

	private void updateSeriesRegistriesValues(ScaleGraphic currentScale, LineChartSeries chartSerie, 
			List<AnalyzerRegistryGui> currentRegistries) {


		BuildingMeterParameterValues buildingMeterSel = BuildingMeterParameterValues.valueOf(buildingMeterSelected);

		String minDate = null;
		String maxDate = null;

		Integer minValue = Integer.MAX_VALUE;
		Integer maxValue = Integer.MIN_VALUE;

		for(AnalyzerRegistryGui registry: currentRegistries) {
			Integer asys = registry.getAsys().intValue();
			Integer hz = registry.getHz().intValue();;
			Integer kwsys = registry.getKwsys().intValue();;
			Integer pfsys = registry.getPfsys().intValue();;
			Integer kvarsys = registry.getKvarsys().intValue();;
			Integer kvasys = registry.getKvasys().intValue();;
			Integer vlnsys = registry.getVlnsys().intValue();;
			Integer vllsys = registry.getVllsys().intValue();;
			String currentTime = registry.getCurrentTimeString();

			switch(buildingMeterSel) {

			case CURRENT:
				chartSerie.set(currentTime,asys);

				if (minValue > asys) {
					minValue = asys;
				}
				if (maxValue < asys) {
					maxValue = asys;
				}

				break;
			case FREQUENCY:
				chartSerie.set(currentTime,hz);

				if (minValue > hz) {
					minValue = hz;
				}
				if (maxValue < hz) {
					maxValue = hz;
				}

				break;
			case POWER:
				chartSerie.set(currentTime,kwsys);

				if (minValue > kwsys) {
					minValue = kwsys;
				}
				if (maxValue < kwsys) {
					maxValue = kwsys;
				}

				break;
			case POWER_FACTOR:
				chartSerie.set(currentTime,pfsys);

				if (minValue > pfsys) {
					minValue = pfsys;
				}
				if (maxValue < pfsys) {
					maxValue = pfsys;
				}

				break;
			case REACTIVE_POWER:
				chartSerie.set(currentTime,kvarsys);

				if (minValue > kvarsys) {
					minValue = kvarsys;
				}
				if (maxValue < kvarsys) {
					maxValue = kvarsys;
				}

				break;
			case VOLT_AMPERE:
				chartSerie.set(currentTime,kvasys);

				if (minValue > kvasys) {
					minValue = kvasys;
				}
				if (maxValue < kvasys) {
					maxValue = kvasys;
				}

				break;
			case VOLTAGE:
				chartSerie.set(currentTime,vlnsys);

				if (minValue > vlnsys) {
					minValue = vlnsys;
				}
				if (maxValue < vlnsys) {
					maxValue = vlnsys;
				}
				break;
			case VOLTAGE_BETWEEN_PHASES:
				chartSerie.set(currentTime,vllsys);

				if (minValue > vllsys) {
					minValue = vllsys;
				}
				if (maxValue < vllsys) {
					maxValue = vllsys;
				}

				break;
			default:
				chartSerie.set(currentTime,kwsys);
				break;
			}

			//Get Max Date for Graphic
			if(maxDate == null || registry.getCurrentTimeString().compareTo( maxDate ) > 0) {
				maxDate = registry.getCurrentTimeString();
			}

			//Get Min Date for Graphic
			if(minDate == null || registry.getCurrentTimeString().compareTo( minDate) < 0) {
				minDate = registry.getCurrentTimeString();
			}

		}

		Axis xAxis = new DateAxis();
		xAxis.setMax(maxDate);
		xAxis.setMin(minDate);


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

		lineModel.getAxes().put(AxisType.X,xAxis);

		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setTickFormat("%d");
		yAxis.setLabel(buildingMeterSel.getLabel() + " (" + buildingMeterSel.getUnit() + ")");
		yAxis.setMax( new Double(maxValue + maxValue*0.10).intValue() );
		yAxis.setMin( new Double(minValue - minValue*0.10).intValue() );
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

	public List<BuildingMeterParameterValues> getBuildingMeterQuickAnalysis() {
		return buildingMeterQuickAnalysis;
	}

	public void setBuildingMeterQuickAnalysis(List<BuildingMeterParameterValues> buildingMeterQuickAnalysis) {
		this.buildingMeterQuickAnalysis = buildingMeterQuickAnalysis;
	}


	public Boolean getNextAndPreviousSelected() {
		return nextAndPreviousSelected;
	}


	public void setNextAndPreviousSelected(Boolean nextAndPreviousSelected) {
		this.nextAndPreviousSelected = nextAndPreviousSelected;
	}

	

}
