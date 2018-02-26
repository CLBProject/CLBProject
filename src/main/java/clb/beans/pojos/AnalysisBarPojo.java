package clb.beans.pojos;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

public class AnalysisBarPojo {

    private LineChartModel lineModel;

    private ChartSeries chartSeriesAl1;
    private ChartSeries chartSeriesAl2;
    private ChartSeries chartSeriesAl3;


    public AnalysisBarPojo(){

        lineModel = new LineChartModel();
        lineModel.setZoom(true);
        lineModel.setTitle( "" );
        lineModel.setLegendPosition("se");

        chartSeriesAl1 = new ChartSeries();
        chartSeriesAl1.setLabel( "AL1" );

        chartSeriesAl2 = new ChartSeries();
        chartSeriesAl2.setLabel( "AL2" );

        chartSeriesAl3 = new ChartSeries();
        chartSeriesAl3.setLabel( "AL3" );

        lineModel.addSeries(chartSeriesAl1);
        lineModel.addSeries(chartSeriesAl2);
        lineModel.addSeries(chartSeriesAl3);
        
        fillGraphicForYearData(new ArrayList());
    }

    public void fillGraphicForYearData(List monthAverage){

        chartSeriesAl1.getData().clear();
        chartSeriesAl2.getData().clear();
        chartSeriesAl3.getData().clear();
        
        chartSeriesAl1.set(1,300);
        chartSeriesAl1.set(2,600);
        chartSeriesAl1.set(3,300);
        chartSeriesAl1.set(4,900);
        chartSeriesAl1.set(5,300);
        
        chartSeriesAl2.set(1,200);
        chartSeriesAl2.set(2,100);
        chartSeriesAl2.set(3,500);
        chartSeriesAl2.set(4,830);
        chartSeriesAl2.set(5,400);
        
        chartSeriesAl3.set(1,100);
        chartSeriesAl3.set(2,100);
        chartSeriesAl3.set(3,400);
        chartSeriesAl3.set(4,800);
        chartSeriesAl3.set(5,900);

        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Month Average");

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Power");

        yAxis.setMin(0);
        //yAxis.setMax(new Double(maxValue + maxValue*0.05).intValue());

    }

    public boolean hasValues(){
        return chartSeriesAl1.getData().size() > 0;
    }

    public boolean updateSeries1(boolean aL1Check) {
        if(!aL1Check){  
            if(lineModel.getSeries().size() == 1){
                return true;
            }
            else lineModel.getSeries().remove(chartSeriesAl1);
        }
        else lineModel.getSeries().add(chartSeriesAl1);

        return aL1Check;
    }

    public boolean updateSeries2(boolean aL2Check) {
        if(!aL2Check){
            if(lineModel.getSeries().size() == 1){
                return true;
            }
            else lineModel.getSeries().remove(chartSeriesAl2);
        }
        else lineModel.getSeries().add(chartSeriesAl2);

        return aL2Check;
    }

    public boolean updateSeries3(boolean aL3Check) {
        if(!aL3Check){
            if(lineModel.getSeries().size() == 1){
                aL3Check = true;
            }
            else lineModel.getSeries().remove(chartSeriesAl3);
        }
        else lineModel.getSeries().add(chartSeriesAl3);

        return aL3Check;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel( LineChartModel lineModel ) {
        this.lineModel = lineModel;
    }
    
    

}
