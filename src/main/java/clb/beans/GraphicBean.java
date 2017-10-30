package clb.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

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

	private boolean aL1Check = true;

	private boolean aL2Check = true;

	private boolean aL3Check = true;

	@PostConstruct
	public void init(){
		lineModel = new LineChartModel();
		lineModel.getAxes().put(AxisType.X, new CategoryAxis("Hours"));

		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Power");
		yAxis.setMin(0);
		yAxis.setMax(1000);

		ChartSeries seriesAL1 = new ChartSeries();
		seriesAL1.setLabel("AL1");

		ChartSeries seriesAL2 = new ChartSeries();
		seriesAL2.setLabel("AL2");

		ChartSeries seriesAL3 = new ChartSeries();
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

				if(analyzerRegObj.getCurrenttime().endsWith(":00:00") &&
						Integer.parseInt(analyzerRegObj.getCurrenttime().split(":")[0]) % 2 == 0 || data.size()-1 == i){ 

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

	}

	public void updateChartSeries(){
		lineModel.getSeries().remove(0);
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
