package clb.beans.pojos;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import clb.global.QuickAnalysisVariables;

public class AnalysisGraphicPojo {

    private LineChartModel lineModel;

    private List<ChartSeries> chartSeries;

    public AnalysisGraphicPojo(List<GraphicTimeValuePojo> values){

        lineModel = new LineChartModel();
        lineModel.setZoom(true);
        lineModel.setTitle( "" );
        lineModel.setLegendPosition("se");
        
        chartSeries = new ArrayList<ChartSeries>();

        for(QuickAnalysisVariables variable : QuickAnalysisVariables.values()) {
            ChartSeries chartSerie = new ChartSeries(variable.getLabel());
            chartSeries.add(chartSerie);
            lineModel.addSeries( chartSerie );
            
        }
        
        fillGraphicForYearData(values);
    }

    public void fillGraphicForYearData(List<GraphicTimeValuePojo> values){

        chartSeries.stream().forEach(  serie -> {

            serie.getData().clear();
            
            serie.set(1,300);
            serie.set(2,600);
            serie.set(3,300);
            serie.set(4,900);
            serie.set(5,300);
            
        });

        values.stream().forEach( value -> value.getGraphicVariableValues() );

        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Month Average");

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Power");

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
