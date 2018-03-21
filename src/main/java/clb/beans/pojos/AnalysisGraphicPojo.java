package clb.beans.pojos;

import java.util.ArrayList;
import java.util.List;

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

public class AnalysisGraphicPojo {

    private LineChartModel lineModel;
    private List<ChartSeries> chartSeries;

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

        chartSeries = new ArrayList<ChartSeries>();

        buildingMetersObject.stream().forEach(  
                buildingMeterObject -> buildingMeterObject.getBuildingMeterParameters().stream().forEach(  
                        buildingMeterParameterObject -> {
                            final LineChartSeries serie = new LineChartSeries(buildingMeterParameterObject.getName());
                            serie.setShowMarker( false );
                            chartSeries.add( serie );
                            lineModel.addSeries( serie );
                        }
                        )
                );

        fillGraphicForYearData(registries, ScaleGraphic.HOUR);
    }

    public void fillGraphicForYearData(List<AnalyzerRegistryObject> registries, ScaleGraphic currentScale){

        //Clear All Data
        lineModel.getSeries().stream().forEach( serie -> serie.getData().clear() );

        chartSeries.stream().forEach( serie ->  registries.stream().forEach(  
                registry -> {
                    if(registry!= null && !registry.equals( "" )) {
                        switch(BuildingMeterParameterValues.valueOf(serie.getLabel())) {
                            case AL1:
                                serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getAl1());
                                break;
                            default: 
                                break;
                        }

                    }
                }
                ));

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
                
        }

        lineModel.getAxes().put(AxisType.X,xAxis);
        
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Energy Values");

        yAxis.setMin(0.0);

    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel( LineChartModel lineModel ) {
        this.lineModel = lineModel;
    }

    public List<ChartSeries> getChartSeries() {
        return chartSeries;
    }

    public void setChartSeries( List<ChartSeries> chartSeries ) {
        this.chartSeries = chartSeries;
    }



}
