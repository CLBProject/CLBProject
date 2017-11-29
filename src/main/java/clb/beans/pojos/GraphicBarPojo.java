package clb.beans.pojos;

import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import clb.beans.enums.ScaleGraphic;
import clb.business.objects.MonthAverageObject;

public class GraphicBarPojo {

    private BarChartModel barModel;

    private ChartSeries chartSeriesAl1;
    private ChartSeries chartSeriesAl2;
    private ChartSeries chartSeriesAl3;


    public GraphicBarPojo(){

        barModel = new BarChartModel();
        barModel.setZoom(true);
        barModel.setTitle( "" );
        barModel.setLegendPosition("se");

        chartSeriesAl1 = new ChartSeries();
        chartSeriesAl1.setLabel( "AL1" );

        chartSeriesAl2 = new ChartSeries();
        chartSeriesAl2.setLabel( "AL2" );

        chartSeriesAl3 = new ChartSeries();
        chartSeriesAl3.setLabel( "AL3" );

        barModel.addSeries(chartSeriesAl1);
        barModel.addSeries(chartSeriesAl2);
        barModel.addSeries(chartSeriesAl3);
    }

    public void fillGraphicForMonthData( List<MonthAverageObject> monthAverage ) {

        double maxValue = 0;

        chartSeriesAl1.getData().clear();
        chartSeriesAl2.getData().clear();
        chartSeriesAl3.getData().clear();

        for(MonthAverageObject monthInfo : monthAverage){

            chartSeriesAl1.set(monthInfo.getDay(), monthInfo.getAl1Average());
            chartSeriesAl2.set(monthInfo.getDay(), monthInfo.getAl2Average());
            chartSeriesAl3.set(monthInfo.getDay(), monthInfo.getAl3Average());

            if(monthInfo.getAl1Average() > maxValue){
                maxValue = monthInfo.getAl1Average();
            }
            if(monthInfo.getAl1Average() > maxValue){
                maxValue = monthInfo.getAl1Average();
            }
            if(monthInfo.getAl1Average() > maxValue){
                maxValue = monthInfo.getAl1Average();
            }

        }

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Day Average");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Power");

        yAxis.setMin(0);
        yAxis.setMax(new Double(maxValue + maxValue*0.05).intValue());

    }

    public void fillGraphicForYearData(List<MonthAverageObject> monthAverage){

        double maxValue = 0;

        chartSeriesAl1.getData().clear();
        chartSeriesAl2.getData().clear();
        chartSeriesAl3.getData().clear();

        for(MonthAverageObject monthInfo : monthAverage){

            chartSeriesAl1.set(monthInfo.getMonth().getLabel(), monthInfo.getAl1Average());
            chartSeriesAl2.set(monthInfo.getMonth().getLabel(), monthInfo.getAl2Average());
            chartSeriesAl3.set(monthInfo.getMonth().getLabel(), monthInfo.getAl3Average());

            if(monthInfo.getAl1Average() > maxValue){
                maxValue = monthInfo.getAl1Average();
            }
            if(monthInfo.getAl1Average() > maxValue){
                maxValue = monthInfo.getAl1Average();
            }
            if(monthInfo.getAl1Average() > maxValue){
                maxValue = monthInfo.getAl1Average();
            }

        }

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Month Average");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Power");

        yAxis.setMin(0);
        yAxis.setMax(new Double(maxValue + maxValue*0.05).intValue());

    }

    public boolean hasValues(){
        return chartSeriesAl1.getData().size() > 0;
    }

    public boolean updateSeries1(boolean aL1Check) {
        if(!aL1Check){  
            if(barModel.getSeries().size() == 1){
                return true;
            }
            else barModel.getSeries().remove(chartSeriesAl1);
        }
        else barModel.getSeries().add(chartSeriesAl1);

        return aL1Check;
    }

    public boolean updateSeries2(boolean aL2Check) {
        if(!aL2Check){
            if(barModel.getSeries().size() == 1){
                return true;
            }
            else barModel.getSeries().remove(chartSeriesAl2);
        }
        else barModel.getSeries().add(chartSeriesAl2);

        return aL2Check;
    }

    public boolean updateSeries3(boolean aL3Check) {
        if(!aL3Check){
            if(barModel.getSeries().size() == 1){
                aL3Check = true;
            }
            else barModel.getSeries().remove(chartSeriesAl3);
        }
        else barModel.getSeries().add(chartSeriesAl3);

        return aL3Check;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel( BarChartModel barModel ) {
        this.barModel = barModel;
    }


}
