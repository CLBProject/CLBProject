package clb.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;

import clb.business.AnalyzerDataService;
import clb.business.objects.AnalyzerRegistryObject;
import clb.database.entities.DataLogger;

@ViewScoped
@ManagedBean
public class GraphicBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{analyzerDataService}")
	private AnalyzerDataService analyzerDataService;

	private LineChartModel lineModel;

	ChartSeries seriesAL1;
	ChartSeries seriesAL2;
	ChartSeries seriesAL3;

	private boolean aL1Check;
	private boolean aL2Check;
	private boolean aL3Check;

	@PostConstruct
	public void init(){

		aL1Check = true;
		aL2Check = true;
		aL3Check = true;

		lineModel = new LineChartModel();
		lineModel.setZoom(true);

		DateAxis axis = new DateAxis("Hours");
		axis.setTickFormat("%H:%M:%S");


		lineModel.getAxes().put(AxisType.X,axis);

		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Power");
		yAxis.setMin(0);
		yAxis.setMax(1000);

		seriesAL1 = new ChartSeries();
		seriesAL1.setLabel("AL1");

		seriesAL2 = new ChartSeries();
		seriesAL2.setLabel("AL2");

		seriesAL3 = new ChartSeries();
		seriesAL3.setLabel("AL3");

		try {
			List<AnalyzerRegistryObject> data = analyzerDataService.getAnalyzerGraphicalData();

			for(int i=0;i<data.size();i++){
				AnalyzerRegistryObject analyzerRegObj = data.get(i);
				seriesAL1.set(analyzerRegObj.getCurrenttime(), analyzerRegObj.getAl1());
				seriesAL2.set(analyzerRegObj.getCurrenttime(), analyzerRegObj.getAl2());
				seriesAL3.set(analyzerRegObj.getCurrenttime(), analyzerRegObj.getAl3());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		lineModel.addSeries(seriesAL1);
		lineModel.addSeries(seriesAL2);
		lineModel.addSeries(seriesAL3);
	}

	public void createDataLogger(){

		DataLogger dataLogger = new DataLogger();

		analyzerDataService.createDataLogger(dataLogger);

		System.out.println( "Data Logger created successfully!" );
	}

	public void updateChartValues(){
		//TODO Update Chart Values
	}

	public void updateChartSeries1(){
		if(!aL1Check){
			if(lineModel.getSeries().size() == 1){
				aL1Check = true;
			}
			else lineModel.getSeries().remove(seriesAL1);
		}
		else lineModel.getSeries().add(seriesAL1);
	}

	public void updateChartSeries2(){
		if(!aL2Check){
			if(lineModel.getSeries().size() == 1){
				aL2Check = true;
			}
			else lineModel.getSeries().remove(seriesAL2);
		}
		else lineModel.getSeries().add(seriesAL2);
	}

	public void updateChartSeries3(){
		if(!aL3Check){
			if(lineModel.getSeries().size() == 1){
				aL3Check = true;
			}
			else lineModel.getSeries().remove(seriesAL3);
		}
		else lineModel.getSeries().add(seriesAL3);
	}

	public AnalyzerDataService getAnalyzerDataService() {
		return analyzerDataService;
	}


	public void setAnalyzerDataService(AnalyzerDataService analyzerDataService) {
		this.analyzerDataService = analyzerDataService;
	}


	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}


	public boolean isaL1Check() {
		return aL1Check;
	}


	public void setaL1Check(boolean aL1Check) {
		this.aL1Check = aL1Check;
	}


	public boolean isaL2Check() {
		return aL2Check;
	}


	public void setaL2Check(boolean aL2Check) {
		this.aL2Check = aL2Check;
	}


	public boolean isaL3Check() {
		return aL3Check;
	}


	public void setaL3Check(boolean aL3Check) {
		this.aL3Check = aL3Check;
	}


}
