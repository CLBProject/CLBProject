package clb.deprecated;

import java.util.Collection;
import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import clb.business.objects.AnalyzerRegistryObject;

public class GraphicLinearPojo {

    private LineChartModel lineModel;

    private LineChartSeries seriesAL1;
    private LineChartSeries seriesAL2;
    private LineChartSeries seriesAL3;

    public GraphicLinearPojo(){

        lineModel = new LineChartModel();
        lineModel.setZoom(true);

        seriesAL1 = new LineChartSeries();
        seriesAL1.setLabel("AL1");
        seriesAL1.setShowMarker(false);

        seriesAL2 = new LineChartSeries();
        seriesAL2.setLabel("AL2");
        seriesAL2.setShowMarker(false);

        seriesAL3 = new LineChartSeries();
        seriesAL3.setLabel("AL3");
        seriesAL3.setShowMarker(false);

        lineModel.addSeries(seriesAL1);
        lineModel.addSeries(seriesAL2);
        lineModel.addSeries(seriesAL3);

    }

    public void fillGraphicForDayData(Collection<AnalyzerRegistryObject> collection){

        DateAxis axis = new DateAxis("Hours");
        axis.setTickFormat("%H:%M:%S");

        lineModel.getAxes().put(AxisType.X,axis);

        double maxValue = 0;
        double minValue = Integer.MAX_VALUE;

        seriesAL1.getData().clear();
        seriesAL2.getData().clear();
        seriesAL3.getData().clear();

        for(AnalyzerRegistryObject analyzerRegObj : collection){
            seriesAL1.set(analyzerRegObj.getCurrenttime(), analyzerRegObj.getAl1());
            seriesAL2.set(analyzerRegObj.getCurrenttime(), analyzerRegObj.getAl2());
            seriesAL3.set(analyzerRegObj.getCurrenttime(), analyzerRegObj.getAl3());

            if(analyzerRegObj.getAl1() > maxValue){
                maxValue = analyzerRegObj.getAl1();
            }
            if(analyzerRegObj.getAl2() > maxValue){
                maxValue = analyzerRegObj.getAl2();
            }
            if(analyzerRegObj.getAl3() > maxValue){
                maxValue = analyzerRegObj.getAl3();
            }
            
            if(analyzerRegObj.getAl1() < minValue){
            	minValue = analyzerRegObj.getAl1();
            }
            if(analyzerRegObj.getAl2() < minValue){
            	minValue = analyzerRegObj.getAl2();
            }
            if(analyzerRegObj.getAl3() < minValue){
            	minValue = analyzerRegObj.getAl3();
            }

        }

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Power");
        //yAxis.setMin(new Double(minValue - minValue*0.05).intValue());
        //yAxis.setMax(new Double(maxValue + maxValue*0.05).intValue());

    }

    public void fillGraphicForDayHoursData(Collection<AnalyzerRegistryObject> collection){

        DateAxis axis = new DateAxis("Minutes");
        axis.setTickFormat("%H:%M:%S");

        lineModel.getAxes().put(AxisType.X,axis);

        double maxValue = 0;
        double minValue = Integer.MAX_VALUE;

        seriesAL1.getData().clear();
        seriesAL2.getData().clear();
        seriesAL3.getData().clear();

        for(AnalyzerRegistryObject analyzerRegObj : collection){
            seriesAL1.set(analyzerRegObj.getCurrenttime(), analyzerRegObj.getAl1());
            seriesAL2.set(analyzerRegObj.getCurrenttime(), analyzerRegObj.getAl2());
            seriesAL3.set(analyzerRegObj.getCurrenttime(), analyzerRegObj.getAl3());

            if(analyzerRegObj.getAl1() > maxValue){
                maxValue = analyzerRegObj.getAl1();
            }
            if(analyzerRegObj.getAl2() > maxValue){
                maxValue = analyzerRegObj.getAl2();
            }
            if(analyzerRegObj.getAl3() > maxValue){
                maxValue = analyzerRegObj.getAl3();
            }
            
            if(analyzerRegObj.getAl1() < minValue){
            	minValue = analyzerRegObj.getAl1();
            }
            if(analyzerRegObj.getAl2() < minValue){
            	minValue = analyzerRegObj.getAl2();
            }
            if(analyzerRegObj.getAl3() < minValue){
            	minValue = analyzerRegObj.getAl3();
            }

        }

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Power");
        //yAxis.setMin(new Double(minValue - minValue*0.05).intValue());
        //yAxis.setMax(new Double(maxValue + maxValue*0.05).intValue());

    }

    public void fillGraphicForMonthData( List<MonthAverageObject> monthAverage ) {

        double maxValue = 0;
        double minValue = Integer.MAX_VALUE;
        int maxValueMonthDay = 0;

        seriesAL1.getData().clear();
        seriesAL2.getData().clear();
        seriesAL3.getData().clear();

        for(MonthAverageObject monthInfo : monthAverage){

            seriesAL1.set(monthInfo.getDay(), monthInfo.getAl1Average());
            seriesAL2.set(monthInfo.getDay(), monthInfo.getAl2Average());
            seriesAL3.set(monthInfo.getDay(), monthInfo.getAl3Average());

            if(monthInfo.getAl1Average() > maxValue){
                maxValue = monthInfo.getAl1Average();
            }
            if(monthInfo.getAl2Average() > maxValue){
                maxValue = monthInfo.getAl2Average();
            }
            if(monthInfo.getAl3Average() > maxValue){
                maxValue = monthInfo.getAl3Average();
            }
            
            if(monthInfo.getAl1Average() < minValue){
            	minValue = monthInfo.getAl1Average();
            }
            if(monthInfo.getAl2Average() < minValue){
            	minValue = monthInfo.getAl2Average();
            }
            if(monthInfo.getAl3Average() < minValue){
            	minValue = monthInfo.getAl3Average();
            }

            if(monthInfo.getDay() > maxValueMonthDay){
            	maxValueMonthDay = monthInfo.getDay();
            }
        }

        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Day Average");
        xAxis.setTickFormat("%d");
        xAxis.setMin(1);
        xAxis.setMax(maxValueMonthDay);

        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Power");

       // yAxis.setMin(new Double(minValue - minValue*0.05).intValue());;
      //  yAxis.setMax(new Double(maxValue + maxValue*0.05).intValue());

    }

    public boolean updateSeries1(boolean aL1Check) {
        if(!aL1Check){	
            if(lineModel.getSeries().size() == 1){
                return true;
            }
            else lineModel.getSeries().remove(seriesAL1);
        }
        else lineModel.getSeries().add(seriesAL1);

        return aL1Check;
    }

    public boolean updateSeries2(boolean aL2Check) {
        if(!aL2Check){
            if(lineModel.getSeries().size() == 1){
                return true;
            }
            else lineModel.getSeries().remove(seriesAL2);
        }
        else lineModel.getSeries().add(seriesAL2);

        return aL2Check;
    }

    public boolean updateSeries3(boolean aL3Check) {
        if(!aL3Check){
            if(lineModel.getSeries().size() == 1){
                aL3Check = true;
            }
            else lineModel.getSeries().remove(seriesAL3);
        }
        else lineModel.getSeries().add(seriesAL3);

        return aL3Check;
    }

    public boolean hasValues(){
        return seriesAL1.getData().size() > 0;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }


}
