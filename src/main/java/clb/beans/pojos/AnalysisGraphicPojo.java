package clb.beans.pojos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import clb.beans.enums.ScaleGraphic;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.global.BuildingMeterParameterValues;
import clb.global.DateUtils;

public class AnalysisGraphicPojo {

	private LineChartModel lineModel;
	private Map<String,LineChartSeries> lineSeriesBuildingParametersMap;

	private static final int STICK_ANGLE_GRAPHIC_LINE = -50;
	private static final String DATE_HOUR_INTERVAL_GRAPHIC = "300000";
	private static final String DATE_DAY_INTERVAL_GRAPHIC = "3600000";
	private static final String DATE_FORMAT_GRAPHIC = "%H:%M:%S";

	public AnalysisGraphicPojo(List<AnalyzerRegistryObject> registries, List<BuildingMeterObject> buildingMetersObject){

		lineModel = new LineChartModel();
		lineModel.setZoom(true);
		lineModel.setLegendPosition("se");
		lineModel.setMouseoverHighlight( true );
		lineModel.setResetAxesOnResize( true );
		lineModel.setAnimate( true );
		lineModel.setShowPointLabels( false );
		lineModel.setShowDatatip( false );

		lineSeriesBuildingParametersMap = new HashMap<String,LineChartSeries>();

		buildingMetersObject.stream().forEach(  
				buildingMeterObject -> {
					BuildingMeterParameterValues parameter = BuildingMeterParameterValues.valueOf(buildingMeterObject.getLabelKey());
					final LineChartSeries serie = new LineChartSeries(parameter.getLabel());
					serie.setShowMarker( false );
					lineSeriesBuildingParametersMap.put(parameter.name(), serie);
					lineModel.addSeries( serie );
				}
				);

		fillGraphicForYearData(registries, ScaleGraphic.HOUR);
	}

	public Map<String, LineChartSeries> getBuildingMap() {
		return lineSeriesBuildingParametersMap;
	}

	public void setBuildingMap(Map<String, LineChartSeries> buildingMap) {
		this.lineSeriesBuildingParametersMap = buildingMap;
	}

	public void fillGraphicForYearData(List<AnalyzerRegistryObject> registries, ScaleGraphic currentScale){

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

		lineModel.getAxes().put(AxisType.X,xAxis);

		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Energy Values");

		yAxis.setMin(0.0);

		//Clear All Data
		lineModel.getSeries().stream().forEach( serie -> serie.getData().clear() );

		lineSeriesBuildingParametersMap.entrySet().stream().forEach( entry ->  registries.stream().forEach(  
				registry -> {
					
					BuildingMeterParameterValues key = BuildingMeterParameterValues.valueOf(entry.getKey());
					LineChartSeries serie = entry.getValue();
					
					if(registry!= null && !registry.equals( "" )) {
						switch(key) {
						case CURRENT:
							serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getAsys());
							break;
						case FREQUENCY:
							serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getHz());
							break;
						case POWER:
							serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getKwsys());
							break;
						case POWER_FACTOR:
							serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getPfsys());
							break;
						case REACTIVE_POWER:
							serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getKvarsys());
							break;
						case VOLT_AMPERE:
							serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getKvasys());
							break;
						case VOLTAGE:
							serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getVlnsys());
							break;
						case VOLTAGE_BETWEEN_PHASES:
							serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getVllsys());
							break;
						default: 
							break;
						}

					}
				}
				));


	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel( LineChartModel lineModel ) {
		this.lineModel = lineModel;
	}

	public Map<String, LineChartSeries> getLineSeriesBuildingParametersMap() {
		return lineSeriesBuildingParametersMap;
	}

	public void setLineSeriesBuildingParametersMap(
			Map<String, LineChartSeries> lineSeriesBuildingParametersMap) {
		this.lineSeriesBuildingParametersMap = lineSeriesBuildingParametersMap;
	}

	
}
