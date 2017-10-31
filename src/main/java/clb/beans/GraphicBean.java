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
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
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
		lineModel.getAxes().put(AxisType.X, new CategoryAxis("Hours"));

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

			double sumAllAl1 = 0;
			int countAl1 = 0;

			double sumAllAl2 = 0;
			int countAl2 = 0;

			double sumAllAl3 = 0;
			int countAl3 = 0;

			List<AnalyzerRegistryObject> data = analyzerDataService.getAnalyzerGraphicalData();

			String previousCurrentTime = "";

			for(int i=0;i<data.size();i++){

				AnalyzerRegistryObject analyzerRegObj = data.get(i);

				if(analyzerRegObj.getCurrenttime().endsWith(":00:00") || data.size()-1 == i){ 

					if(!previousCurrentTime.equals("")){
						seriesAL1.set(previousCurrentTime, sumAllAl1/countAl1);
						seriesAL2.set(previousCurrentTime, sumAllAl2/countAl2);
						seriesAL3.set(previousCurrentTime, sumAllAl3/countAl3);
					}

					previousCurrentTime = analyzerRegObj.getCurrenttime();

					sumAllAl1 = 0.0;
					countAl1 = 0;
					sumAllAl2 = 0.0;
					countAl2 = 0;
					sumAllAl3 = 0.0;
					countAl3 = 0;
				}
				else {
					sumAllAl1 += analyzerRegObj.getAl1();
					countAl1 ++;
					sumAllAl2 += analyzerRegObj.getAl2();
					countAl2 ++;
					sumAllAl3 += analyzerRegObj.getAl3();
					countAl3 ++;
				}
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
