package clb.beans.pojos;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import clb.beans.enums.ScaleGraphic;
import clb.global.Months;

public class GraphicBarPojo {

    private BarChartModel barModel;

	private ChartSeries chartSeries;

	private Object[] chartData;

	
	public GraphicBarPojo(Object[] chartData, ScaleGraphic scaleGraphic, final String label){

	    barModel = new BarChartModel();
	    barModel.setZoom(true);
	    barModel.setTitle( "" );
        barModel.setLegendPosition("se");
         
        chartSeries = new ChartSeries();
        chartSeries.setLabel( label );

        barModel.addSeries(chartSeries);

		fillGraphicForData(chartData, scaleGraphic);
	}
	
	public void fillGraphicForData(Object[] collection, ScaleGraphic scaleGraphic){
		
		this.chartData = collection;
		
		switch(scaleGraphic){
		case YEAR:
			fillGraphicForDayData(this.chartData);
			break;
		default: 
		}
	}

	private void fillGraphicForDayData(Object[] collection){

		double maxValue = 0;

		chartSeries.getData().clear();

		for(Object monthInfo : collection){
		    
		    Object[] monthInfoParsed = (Object[]) monthInfo;
		    
		    chartSeries.set(Months.getMonthName((Integer)monthInfoParsed[1]), (Double)monthInfoParsed[0]);

			if((Double)monthInfoParsed[0] > maxValue){
				maxValue = (Double)monthInfoParsed[0];
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
		return chartSeries.getData().size() > 0;
	}
	
    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel( BarChartModel barModel ) {
        this.barModel = barModel;
    }

	
}
