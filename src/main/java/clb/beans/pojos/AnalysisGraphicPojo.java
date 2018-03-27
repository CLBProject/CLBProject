package clb.beans.pojos;

import java.util.Date;
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
    private Map<String,Boolean> lineSeriesAvilableBuldingParamatersMap;

    private static final int STICK_ANGLE_GRAPHIC_LINE = -50;
    private static final String DATE_HOUR_INTERVAL_GRAPHIC = "300";
    private static final String DATE_DAY_INTERVAL_GRAPHIC = "3600";
    private static final String DATE_FORMAT_GRAPHIC = "%H:%M:%S";

    public AnalysisGraphicPojo(List<BuildingMeterObject> buildingMetersObject){

        lineModel = new LineChartModel();
        lineModel.setZoom(true);
        lineModel.setLegendPosition("se");
        lineModel.setMouseoverHighlight( true );
        lineModel.setResetAxesOnResize( true );
        lineModel.setAnimate( true );
        lineModel.setShowPointLabels( false );
        lineModel.setShowDatatip( false );

        lineSeriesBuildingParametersMap = new HashMap<String,LineChartSeries>();
        lineSeriesAvilableBuldingParamatersMap = new HashMap<String,Boolean>();

        buildingMetersObject.stream().forEach(  
                buildingMeterObject -> {
                    BuildingMeterParameterValues parameter = BuildingMeterParameterValues.valueOf(buildingMeterObject.getLabelKey());
                    final LineChartSeries serie = new LineChartSeries(parameter.getLabel());
                    serie.setShowMarker( false );
                    lineSeriesBuildingParametersMap.put(parameter.name(), serie);
                    lineSeriesAvilableBuldingParamatersMap.put( parameter.name(), true );
                    lineModel.addSeries( serie );
                }
                );
    }


    public void fillGraphicForData(List<AnalyzerRegistryObject> registries, ScaleGraphic currentScale){

        //Clear All Data
        lineModel.getSeries().stream().forEach( serie -> serie.getData().clear() );

        Date minDate = new Date();
        Date maxDate = null;

        Double minValue = Double.MAX_VALUE;
        Double maxValue = Double.MIN_VALUE;

        for(AnalyzerRegistryObject registry: registries) {

            String currentTime = DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime());
            Double asys = registry.getAsys();
            Double hz = registry.getHz();
            Double kwsys = registry.getKwsys();
            Double pfsys = registry.getPfsys();
            Double kvarsys = registry.getKvarsys();
            Double kvasys = registry.getKvasys();
            Double vlnsys = registry.getVlnsys();
            Double vllsys = registry.getVllsys();

            lineSeriesBuildingParametersMap.get( BuildingMeterParameterValues.CURRENT.name() ).set( currentTime, asys);
            lineSeriesBuildingParametersMap.get( BuildingMeterParameterValues.FREQUENCY.name() ).set( currentTime, hz);
            lineSeriesBuildingParametersMap.get( BuildingMeterParameterValues.POWER.name() ).set( currentTime, kwsys);
            lineSeriesBuildingParametersMap.get( BuildingMeterParameterValues.POWER_FACTOR.name() ).set( currentTime, pfsys);
            lineSeriesBuildingParametersMap.get( BuildingMeterParameterValues.REACTIVE_POWER.name() ).set( currentTime, kvarsys);
            lineSeriesBuildingParametersMap.get( BuildingMeterParameterValues.VOLT_AMPERE.name() ).set( currentTime, kvasys);
            lineSeriesBuildingParametersMap.get( BuildingMeterParameterValues.VOLTAGE.name() ).set( currentTime, vlnsys);
            lineSeriesBuildingParametersMap.get( BuildingMeterParameterValues.VOLTAGE_BETWEEN_PHASES.name() ).set( currentTime, vllsys);

            //Get Max Date for Graphic
            if(maxDate == null || registry.getCurrenttime().compareTo( maxDate ) > 0) {
                maxDate = registry.getCurrenttime();
            }

            //Get Min Date for Graphic
            if(registry.getCurrenttime().compareTo( minDate) < 0) {
                minDate = registry.getCurrenttime();
            }

            if (minValue > asys) {
                minValue = asys;
            }
            if (maxValue < asys) {
                maxValue = asys;
            }

            if (minValue > hz) {
                minValue = hz;
            }
            if (maxValue < hz) {
                maxValue = hz;
            }

            if (minValue > kwsys) {
                minValue = kwsys;
            }
            if (maxValue < kwsys) {
                maxValue = kwsys;
            }
            if (minValue > pfsys) {
                minValue = pfsys;
            }
            if (maxValue < pfsys) {
                maxValue = pfsys;
            }
            if (minValue > kvarsys) {
                minValue = kvarsys;
            }
            if (maxValue < kvarsys) {
                maxValue = kvarsys;
            }
            if (minValue > kvasys) {
                minValue = kvasys;
            }
            if (maxValue < kvasys) {
                maxValue = kvasys;
            }
            if (minValue > vlnsys) {
                minValue = vlnsys;
            }
            if (maxValue < vlnsys) {
                maxValue = vlnsys;
            }
            if (minValue > vllsys) {
                minValue = vllsys;
            }
            if (maxValue < vllsys) {
                maxValue = vllsys;
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
        yAxis.setMax( maxValue );
        yAxis.setMin( minValue );
    }

    /**
     * Changes The Serie to add or remove in the Graphic
     * @param serieToChange
     */
    public void changeSerie(String serieToChange) {

        if(isLastToCheck()) {
            lineSeriesAvilableBuldingParamatersMap.put( serieToChange, true );
        }
        else {
            if(lineSeriesAvilableBuldingParamatersMap.get( serieToChange)) {
                lineModel.addSeries( lineSeriesBuildingParametersMap.get( serieToChange ) );
            }
            else lineModel.getSeries().remove( lineSeriesBuildingParametersMap.get( serieToChange ) );
        }
    }

    private boolean isLastToCheck() {
        return lineSeriesAvilableBuldingParamatersMap.values().stream().filter( value -> value ).count() == 0;
    }

    public Map<String, LineChartSeries> getBuildingMap() {
        return lineSeriesBuildingParametersMap;
    }

    public void setBuildingMap(Map<String, LineChartSeries> buildingMap) {
        this.lineSeriesBuildingParametersMap = buildingMap;
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

    public Map<String, Boolean> getLineSeriesAvilableBuldingParamatersMap() {
        return lineSeriesAvilableBuldingParamatersMap;
    }

    public void setLineSeriesAvilableBuldingParamatersMap( Map<String, Boolean> lineSeriesAvilableBuldingParamatersMap ) {
        this.lineSeriesAvilableBuldingParamatersMap = lineSeriesAvilableBuldingParamatersMap;
    }


}
