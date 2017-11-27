package clb.beans;

import java.util.Collection;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import clb.beans.enums.ScaleGraphic;
import clb.business.objects.AnalyzerRegistryObject;

public class GraphicPojo {

	private LineChartModel lineModel;

	private LineChartSeries seriesAL1;
	private LineChartSeries seriesAL2;
	private LineChartSeries seriesAL3;

	private Collection<?> chartData;

	
	public GraphicPojo(Collection<?> chartData, ScaleGraphic scaleGraphic){

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

		fillGraphicForData(chartData, scaleGraphic);

	}
	
	@SuppressWarnings("unchecked")
	public void fillGraphicForData(Collection<?> collection, ScaleGraphic scaleGraphic){
		
		this.chartData = collection;
		
		switch(scaleGraphic){
		case DAY:
	
			fillGraphicForDayData((Collection<AnalyzerRegistryObject>) this.chartData);

			DateAxis axis = new DateAxis("Hours");
			axis.setTickFormat("%H:%M:%S");

			lineModel.getAxes().put(AxisType.X,axis);
			break;
		default: 
		}
	}

	private void fillGraphicForDayData(Collection<AnalyzerRegistryObject> collection){

		double maxValue = 0;

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

		}

		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Power");
		yAxis.setMin(0);
		yAxis.setMax(new Double(maxValue + maxValue*0.05).intValue());

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

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}


}
