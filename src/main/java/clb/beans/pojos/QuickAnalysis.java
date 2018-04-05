package clb.beans.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import clb.beans.enums.ScaleGraphic;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.global.BuildingMeterParameterValues;
import clb.global.DateUtils;

public class QuickAnalysis {

	private LineChartModel lineModel;

	private static final int STICK_ANGLE_GRAPHIC_LINE = -50;
	private static final String DATE_HOUR_INTERVAL_GRAPHIC = "300";
	private static final String DATE_DAY_INTERVAL_GRAPHIC = "3600";
	private static final String DATE_FORMAT_GRAPHIC = "%H:%M:%S";

	private String buildingMeterSelected;
	private List<BuildingMeterParameterValues> buildingMeterQuickAnalysis;

	private List<AnalyzerRegistryObject> currentRegistries;

	public QuickAnalysis(List<BuildingMeterObject> buildingMetersObject){

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
		lineModel.setResetAxesOnResize( true );
		lineModel.setAnimate( true );
		lineModel.setShowPointLabels( false );
		lineModel.setShowDatatip( false );

		//Default
		final LineChartSeries serie = new LineChartSeries(BuildingMeterParameterValues.POWER.getLabel());
		serie.setShowMarker( false );
		lineModel.addSeries( serie );

		buildingMeterSelected = BuildingMeterParameterValues.POWER.name();

		currentRegistries = new ArrayList<AnalyzerRegistryObject>();
	}


	public void fillGraphicForData(List<AnalyzerRegistryObject> registries, ScaleGraphic currentScale){

		this.currentRegistries = registries;

		//Clear All Data
		lineModel.getSeries().stream().forEach( serie -> serie.getData().clear() );

		if(registries.size() > 0) {
			updateSeriesRegistriesValues(currentScale);
		}
	}

	/**
	 * Changes The Serie to add or remove in the Graphic
	 * @param serieToChange
	 */
	public void changeSerie(ScaleGraphic currentScale) {

		Map<Object, Number> dataSerie = lineModel.getSeries().get(0).getData();

		lineModel.clear();

		BuildingMeterParameterValues buildingMeterParamValue = BuildingMeterParameterValues.valueOf(buildingMeterSelected);

		final LineChartSeries serie = new LineChartSeries(buildingMeterParamValue.getLabel());
		serie.setShowMarker( false );
		serie.setData(dataSerie);
		lineModel.addSeries( serie );

		updateSeriesRegistriesValues(currentScale);
	}

	private void updateSeriesRegistriesValues(ScaleGraphic currentScale) {

		Date minDate = new Date();
		Date maxDate = null;

		Double minValue = Double.MAX_VALUE;
		Double maxValue = Double.MIN_VALUE;

		for(AnalyzerRegistryObject registry: currentRegistries) {
			Double asys = registry.getAsys();
			Double hz = registry.getHz();
			Double kwsys = registry.getKwsys();
			Double pfsys = registry.getPfsys();
			Double kvarsys = registry.getKvarsys();
			Double kvasys = registry.getKvasys();
			Double vlnsys = registry.getVlnsys();
			Double vllsys = registry.getVllsys();
			String currentTime = DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime());

			for(ChartSeries chartSerie: lineModel.getSeries()) {
				switch(BuildingMeterParameterValues.valueOf(buildingMeterSelected)) {

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
			}

			//Get Max Date for Graphic
			if(maxDate == null || registry.getCurrenttime().compareTo( maxDate ) > 0) {
				maxDate = registry.getCurrenttime();
			}

			//Get Min Date for Graphic
			if(registry.getCurrenttime().compareTo( minDate) < 0) {
				minDate = registry.getCurrenttime();
			}

		}

		Axis xAxis = new DateAxis("Time");
		xAxis.setTickAngle(STICK_ANGLE_GRAPHIC_LINE);

		switch(currentScale) {
		case HOUR:
			//5 minutes interval
			xAxis.setTickInterval( DATE_HOUR_INTERVAL_GRAPHIC );
			xAxis.setTickFormat(DATE_FORMAT_GRAPHIC);

			break;
		case DAY:
			//5 minutes interval
			xAxis.setTickInterval( DATE_DAY_INTERVAL_GRAPHIC );
			xAxis.setTickFormat(DATE_FORMAT_GRAPHIC);
			break;
		default:
			break;

		}
		xAxis.setMin( DateUtils.getInstance().convertDateToSimpleStringFormat( minDate ));
		xAxis.setMax( DateUtils.getInstance().convertDateToSimpleStringFormat( maxDate ));

		lineModel.getAxes().put(AxisType.X,xAxis);

		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Energy Values");
		yAxis.setMax( maxValue + maxValue*0.10 );
		yAxis.setMin( minValue - minValue*0.10 );
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


}
