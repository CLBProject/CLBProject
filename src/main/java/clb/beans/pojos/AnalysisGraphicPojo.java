package clb.beans.pojos;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingMeterObject;
import clb.global.BuildingMeterParameterValues;

public class AnalysisGraphicPojo {

    private LineChartModel lineModel;

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
                        buildingMeterParameterObject -> registries.stream().forEach(  
                                registry -> {
                                    if(registry!= null && !registry.equals( "" )) {
                                        BuildingMeterParameterValues bmpValue = BuildingMeterParameterValues.valueOf( buildingMeterParameterObject.getName()  );

                                        if(bmpValue != null) {
                                            final ChartSeries serie = new LineChartSeries();
                                            
                                            switch(bmpValue) {
                                                case AL1:
                                                    serie.set(registry.getCurrenttime(),registry.getAl1());
                                                    break;
                                                default: break;
                                            }
                                            
                                            lineModel.addSeries( serie );
                                        }
                                    }
                                }
                                )
                        )
                );

        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Date Time");

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Energy Values");

        yAxis.setMin(0);
        //yAxis.setMax(new Double(maxValue + maxValue*0.05).intValue());

    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel( LineChartModel lineModel ) {
        this.lineModel = lineModel;
    }



}
