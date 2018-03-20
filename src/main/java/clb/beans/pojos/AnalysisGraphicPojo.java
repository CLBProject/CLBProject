package clb.beans.pojos;

import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.global.BuildingMeterParameterValues;
import clb.global.DateUtils;

public class AnalysisGraphicPojo {

    private LineChartModel lineModel;
    
    private static final int STICK_ANGLE_GRAPHIC_LINE = -50;
    private static final String DATE_INTERVAL_GRAPHIC = "300000";
    private static final String DATE_FORMAT_GRAPHIC = "%H:%M:%S";

    public AnalysisGraphicPojo(List<AnalyzerRegistryObject> registries, List<BuildingMeterObject> buildingMetersObject){

        lineModel = new LineChartModel();
        lineModel.setZoom(true);
        lineModel.setTitle( "" );
        lineModel.setLegendPosition("se");

        fillGraphicForYearData(registries, buildingMetersObject);
    }

    private void fillGraphicForYearData(List<AnalyzerRegistryObject> registries, List<BuildingMeterObject> buildingMetersObject){

        buildingMetersObject.stream().forEach(  
                buildingMeterObject -> buildingMeterObject.getBuildingMeterParameters().stream().forEach(  
                        buildingMeterParameterObject -> {
                            final LineChartSeries serie = new LineChartSeries(buildingMeterParameterObject.getName());
                            registries.stream().forEach(  
                                    registry -> {
                                        if(registry!= null && !registry.equals( "" )) {
                                            BuildingMeterParameterValues bmpValue = BuildingMeterParameterValues.valueOf( buildingMeterParameterObject.getName()  );

                                            if(bmpValue != null) {

                                                switch(bmpValue) {
                                                    case AL1:
                                                        serie.set(DateUtils.getInstance().convertDateToSimpleStringFormat( registry.getCurrenttime()),registry.getAl1());
                                                        break;
                                                    default: 
                                                        break;
                                                }


                                            }
                                        }
                                    });
                            lineModel.addSeries( serie );
                        }
                        )

                );
        Axis xAxis = new DateAxis("Time");
        xAxis.setTickAngle(STICK_ANGLE_GRAPHIC_LINE);
        
        //5 minutes interval
        xAxis.setTickInterval( DATE_INTERVAL_GRAPHIC );
        xAxis.setTickFormat(DATE_FORMAT_GRAPHIC);
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



}
