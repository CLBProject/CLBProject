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

	
	public GraphicBarPojo(ScaleGraphic scaleGraphic){

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
	
	public void fillGraphicForData(List<MonthAverageObject> monthAverage, ScaleGraphic scaleGraphic){
		
		
		switch(scaleGraphic){
		case YEAR:
			fillGraphicForDayData(monthAverage);
			break;
		default: 
		}
	}

	private void fillGraphicForDayData(List<MonthAverageObject> monthAverage){

		double maxValue = 0;

		chartSeriesAl1.getData().clear();
		chartSeriesAl2.getData().clear();
		chartSeriesAl3.getData().clear();

		for(MonthAverageObject monthInfo : monthAverage){
		    
		    chartSeriesAl1.set(monthInfo.getMonth().name(), monthInfo.getAl1Average());
		    chartSeriesAl2.set(monthInfo.getMonth().name(), monthInfo.getAl2Average());
		    chartSeriesAl3.set(monthInfo.getMonth().name(), monthInfo.getAl3Average());

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
        xAxis.setLabel("Month");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Power");
		
		yAxis.setMin(0);
		yAxis.setMax(new Double(maxValue + maxValue*0.05).intValue());

	}

	public boolean hasValues(){
		return chartSeriesAl1.getData().size() > 0;
	}
	
    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel( BarChartModel barModel ) {
        this.barModel = barModel;
    }

	
}
